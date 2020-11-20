package main.java.arm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import main.java.ir3.Label;
import main.java.ir3.MdBody3;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Id3;
import main.java.ir3.stmt.GotoStatement3;
import main.java.ir3.stmt.IfStatement3;
import main.java.ir3.stmt.LabelStatement;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.statement.ReturnStatement;

class Elem {
    public String getId() {
        return id;
    }

    public ArrayList<String> getNeigh() {
        return neigh;
    }

    String id;
    ArrayList<String> neigh;

    public Elem(String id, ArrayList<String> neigh) {
        this.id = id;
        this.neigh = neigh;
    }

    @Override
    public String toString() {
        return String.format("[id='%s', neigh=%s]", id, String.join(", ", neigh));
    }
}

public class ControlFlowGraph {
    private List<VarDecl3> variableDeclarations;
    private List<Stmt3> stmts;

    private HashMap<String, Integer> firstDefTable;
    private HashMap<String, Integer> lastUseTable;

    private HashMap<Integer, CFGNode> seen;

    private CFGNode head;
    private ArrayList<CFGNode> topoSortedStmts;
    private HashSet<Integer> topoSeen;

    private Allocation allocation;

    private HashMap<String, ArrayList<String>> interferenceAdjList;


    public ControlFlowGraph() {
        this.seen = new HashMap<Integer, CFGNode>();
        firstDefTable = new HashMap<String, Integer>();
        lastUseTable = new HashMap<String, Integer>();
        topoSortedStmts = new ArrayList<>();
        topoSeen = new HashSet<>();
    }
// Todo deadcode elimination here!


    public void build(List<Argument> arguments, MdBody3 body) {
        variableDeclarations = body.getVariableDeclarations();
        stmts = body.getStmts();
        arguments.forEach(arg -> variableDeclarations.add(arg.toVarDecl3()));
        arguments.forEach(arg -> firstDefTable.put(arg.getId().name, -1));

//        variableDeclarations.forEach(arg -> {
//            firstDefTable.put(arg.getId().getName(), Integer.MAX_VALUE); // init max value
//            lastUseTable.put(arg.getId().getName(), -1);
//        });

        head = explore(0);
        buildTopoOrdering();

        buildInterferenceGraph();
        allocation = findAllocationValidAllocation(new ArrayList<>());
    }

    private void buildTopoOrdering() {
        // topo sort via dfs post ordering
        dfs(head);
        Collections.reverse(topoSortedStmts);


        // derive live range of the the variables
        for (int i = 0; i < topoSortedStmts.size(); i++) {
            CFGNode curr = topoSortedStmts.get(i);

            Id3 def = curr.getStmt().getDef();

            if (def != null && !firstDefTable.containsKey(def.getName())) {
                firstDefTable.put(def.getName(), i);
            }

            for (Id3 use : curr.getStmt().getUses()) {
                // get use
                lastUseTable.put(use.getName(), i);
            }
        }
    }

    private HashMap<String, ArrayList<String>> copyInterferenceGraph(ArrayList<String> spilled) {
        HashMap<String, ArrayList<String>> hm = new HashMap<>();

        for (Map.Entry<String, ArrayList<String>> item : interferenceAdjList.entrySet()) {
            String key = item.getKey();
            ArrayList<String> neighbourList = new ArrayList<>(item.getValue());

            if (!spilled.contains(key)) {
                neighbourList.removeAll(spilled);
                hm.put(key, neighbourList);
            }


        }
        return hm;
    }

    private Allocation findAllocationValidAllocation(ArrayList<String> spilled) {
        Allocation allocation = new Allocation(spilled);
        // find satisfying assigment via graph coloring (simplify and select heuristic)

        int K = Allocation.NUM_REGISTER;

        HashMap<String, ArrayList<String>> G = copyInterferenceGraph(spilled);

        // Stage 1 simplify
        Stack<Elem> stack = new Stack<>();
        String toRemove = null;

        while (!G.isEmpty()) {
            toRemove = null;
            for (Map.Entry<String, ArrayList<String>> item : G.entrySet()) {
                String key = item.getKey();
                ArrayList<String> neighbourList = item.getValue();

                if (neighbourList.size() < K) {
                    stack.push(new Elem(key, neighbourList));
                    toRemove = key;
                    break;

                }
            }

            if (toRemove == null) {
                // need to spill and reset
                // spill var with the highest degree
                String id = findElemWithMaxDegree(G);
                ArrayList<String> nspilled = new ArrayList<>(spilled);
                nspilled.add(id);
                System.out.println("spilling " + id);
                return findAllocationValidAllocation(nspilled);

            } else {
                G.remove(toRemove);
                for (ArrayList<String> neigh : G.values()) {
                    neigh.remove(toRemove);
                }

            }
        }

        // Stage 2 Select
        HashMap<String, String> assigned = new HashMap<>();
        // init allowed set
//        for (Elem e: stack) {
//            assigned.put(e.getId(), new ArrayList<>(Allocation.registers));
//        }

        while (!stack.isEmpty()) {
            Elem elem = stack.pop();
            String id = elem.getId();
            List<String> neig = elem.getNeigh();


            ArrayList<String> forbidden = new ArrayList<>();
            for (String neigId : neig) {
                forbidden.add(assigned.get(neigId));
            }

            String assign = null;
            for (String reg : Allocation.registers) {
                if (!forbidden.contains(reg)) {
                    assign = reg;
                    allocation.assign(id, reg);
                    assigned.put(id, reg);
                    break;
                }
            }

            if (assign == null) {
                System.out.println("err: shouldnt get here, should always have at least one color to color.");
                System.out.println(elem);
                break;
            }
        }


        System.out.println(allocation);

        return allocation;
    }

    // Highest Degree Eviction Heuristic
    private String findElemWithMaxDegree(HashMap<String, ArrayList<String>> g) {
        int best = Integer.MIN_VALUE;
        String id = null;

        for (Map.Entry<String, ArrayList<String>> item : g.entrySet()) {
            String key = item.getKey();
            ArrayList<String> value = item.getValue();

            if (value.size() > best) {
                best = value.size();
                id = key;
            }
        }

        return id;

    }

    private void buildInterferenceGraph() {
        interferenceAdjList = new HashMap<>();
        for (VarDecl3 var : variableDeclarations) {
            interferenceAdjList.put(var.getId().getName(), new ArrayList<>());
        }


        for (int i = 0; i < variableDeclarations.size() - 1; i++) {
            for (int j = i + 1; j < variableDeclarations.size(); j++) {
                String fistVar = variableDeclarations.get(i).getId().getName();
                String secondVar = variableDeclarations.get(j).getId().getName();
                if (isInterfering(variableDeclarations.get(i), variableDeclarations.get(j))) {
                    interferenceAdjList.get(fistVar).add(secondVar);
                    interferenceAdjList.get(secondVar).add(fistVar);
                }
            }
        }
    }

    private boolean isInterfering(VarDecl3 decl1, VarDecl3 decl2) {
        // https://stackoverflow.com/questions/36035074/how-can-i-find-an-overlap-between-two-given-ranges/36035369
        int a = firstDefTable.getOrDefault(decl1.getId().getName(), -1);
        int b = firstDefTable.getOrDefault(decl2.getId().getName(), -1);
        int c = lastUseTable.getOrDefault(decl1.getId().getName(), -1);
        int d = lastUseTable.getOrDefault(decl2.getId().getName(), -1);
        return a >= d || c >= b;
    }


    private void dfs(CFGNode current) {
        if (!topoSeen.contains(current.getIndex())) {
            topoSeen.add(current.getIndex());
            for (CFGNode neig : current.getNext()) {
                dfs(neig);
            }
            topoSortedStmts.add(current);
        }


    }

    private CFGNode explore(int i) {
        if (i < 0 || i >= stmts.size()) {
            return null;
        }

        Stmt3 stmt = stmts.get(i);
        CFGNode node = new CFGNode(stmt, i);
        if (seen.containsKey(i)) {
            node.addNext(seen.get(i));
            return node;
        } else {
            seen.put(i, node);
        }


        if (stmt instanceof IfStatement3) {
            IfStatement3 ifStatement = (IfStatement3) stmt;
            node.addNext(explore(find(ifStatement.getLabel())));
            node.addNext(explore(i + 1));
        } else if (stmt instanceof GotoStatement3) {
            GotoStatement3 gotoStmt = (GotoStatement3) stmt;
            node.addNext(explore(find(gotoStmt.getLabel())));
        } else if (!(stmt instanceof ReturnStatement)) {
            node.addNext(explore(i + 1));
        }

        return node;
    }

    private int find(Label label) {
        for (int i = 0; i < stmts.size(); i++) {
            if (stmts.get(i) instanceof LabelStatement) {
                LabelStatement labelStatement = (LabelStatement) stmts.get(i);
                if (labelStatement.getLabel().equals(label)) {
                    return i;
                }
            }
        }
        System.out.println("err: shouldnt get here");
        return -1;
    }


    public void print() {
        System.out.println("=== IR WITH INDEX ===");
        for (int i = 0; i < stmts.size(); i++) {
            System.out.println(String.format("line %s -> %s", i, stmts.get(i)));
        }
        System.out.println("---");

        for (int i = 0; i < stmts.size(); i++) {
            if (seen.containsKey(i)) {
                System.out.println(String.format("%d: %s",
                    i,
                    seen.get(i).getNext().stream().map(x -> x.getIndex().toString()).collect(Collectors.joining(", "))));
            } else {
                System.out.println(String.format("%d: dead", i));
            }
        }

        System.out.println("=== TOPO SORTED ===");

        for (CFGNode node : topoSortedStmts) {
            System.out.println(String.format("%d: %s", node.getIndex(), node.getStmt()));
        }
        System.out.println("=== END TOPO SORTED ===");


        System.out.println("=== FIRST AND LAST USE ===");

        for (VarDecl3 decl : variableDeclarations) {
            System.out.println(String.format("%s: first def (%d), last use (%d)",
                decl.getId().getName(),
                firstDefTable.getOrDefault(decl.getId().getName(), -1),
                lastUseTable.getOrDefault(decl.getId().getName(), -1)));
        }
        System.out.println("=== END FIRST AND LAST USE ===");

        printInterferenceGraph();
    }

    private void printInterferenceGraph() {
        System.out.println("=== Interference Graph ===");
        for (VarDecl3 var : variableDeclarations) {
            String varId = var.getId().getName();
            if (interferenceAdjList.containsKey(varId)) {
                System.out.println(String.format("%s: %s",
                    var.getId().getName(),
                    String.join(", ", interferenceAdjList.get(varId))
                ));
            } else {
                System.out.println(String.format("%s: spilled", varId));
            }

        }

        System.out.println("=== End Interference Graph ===");
    }
}
