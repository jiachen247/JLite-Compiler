package main.java.ir3;

import java.util.HashMap;
import java.util.List;

import main.java.arm.Allocation;
import main.java.arm.ControlFlowGraph;
import main.java.arm.GlobalOffsetTable;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class CMtd3 {
    private BasicType type;

    public Id getId() {
        return id;
    }

    private Id id;

    public List<Argument> getArguments() {
        return arguments;
    }

    private List<Argument> arguments;
    private MdBody3 body;

    public Boolean getIsMain() {
        return isMain;
    }

    private Boolean isMain;

    public HashMap<String, Integer> getOffsetTable() {
        return offsetTable;
    }

    public HashMap<String, Integer> offsetTable;

    private ControlFlowGraph graph;

    private Allocation allocation;

    public CMtd3(BasicType type, Id id, List<Argument> arguments, MdBody3 body, Boolean isMain) {
        this.type = type;
        this.id = id;
        this.arguments = arguments;
        this.body = body;
        this.offsetTable = new HashMap<>();
        this.isMain = isMain;

        graph = new ControlFlowGraph();
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}\n",
            type.toString(),
            id.toString(),
            Helper.getInstance().concat(arguments),
            body.toString());
    }

    public String generateArm(boolean toOptimize, List<CData3> classes, boolean debug) {
        String entryLabel = id.toString();
        String exitLabel = String.format("%s_exit", entryLabel);
        if (isMain) {
            entryLabel = "main";
        }

        buildAllocation();
        GlobalOffsetTable.getInstance().setAllocation(allocation);
        List<String> spilled = allocation.getSpilled();

        int frameSize = 28 + 4 * spilled.size(); // 28 for {fp, lr, v0-v1}

        // Store arguments in a1 to a4 if arg length < 5 else push them onto the stack in reverse order
        if (arguments.size() > 4) {
            // arguments spilled but still on stack (prevent double count)
            for (Argument a : arguments) {
                if (!allocation.isOnRegister(a.getId().name)) {
                    frameSize -= 4;
                }
            }
        }

        String prolog = buildProlog(entryLabel, frameSize);
        String bodyArm = body.generateArm();
        String epilogue = buildEpilogue(exitLabel);

        if (debug) {
            printOffsetTable();
            printInterferenceGraph();
            System.out.println(allocation);
        }

        return String.format("%s" +
                "%s\n" +
                "%s\n",
            prolog, bodyArm, epilogue);
    }

    private void buildAllocation() {
        allocation = graph.build(arguments, body);
    }

    private void printInterferenceGraph() {
        System.out.println(String.format("--- Inteference Graphh (%s) ---", this.id.name));
        graph.print();
        System.out.println("-----\n");
    }

    private void printOffsetTable() {
        System.out.println(String.format("--- Offset Table (%s) ---", this.id.name));
        offsetTable.entrySet().forEach(entry -> {
            System.out.println(String.format("[%s: %s]", entry.getKey(), entry.getValue()));
        });
        System.out.println("-----\n");
    }

    private String buildStack(Integer frameSize) {
        // stack grows toward lower address

        StringBuilder sb = new StringBuilder();

        // MIGHT BE 28 instead
        int offset = -28; // for fp, lr and v0-5
        /*
            If len(args) <= 4
               fp -> | fp, lr, v0-v5 | arg n to 1 | local variables | temps | <- sp

           If len(args) > 4
                args n to 1 | fp -> fp, lr, v0-v5 | local variables | temps | <- sp

         */

        if (arguments.size() <= 4) {
            // Called with args in registers
            int regInd = 1; // a1 to a4
            for (Argument arg : arguments) {
                if (allocation.isSpilled(arg.getId().name)) {
                    sb.append(String.format("    str a%d, [fp, #%d]\n", regInd, offset));
                    offsetTable.put(arg.id.name, offset);
                    offset -= 4;
                } else {
                    sb.append(String.format("    mov %s, a%d\n",
                        allocation.lookup(arg.getId().name), regInd));

                }
                regInd += 1;

            }

        } else {
            // args already on the stack below bp
            int argPtr = 4;
            for (Argument arg : arguments) {
                if (allocation.isSpilled(arg.getId().name)) {
                    offsetTable.put(arg.id.name, argPtr);

                } else {
                    sb.append(String.format("    ldr %s, [fp, #%d]\n",
                        allocation.lookup(arg.getId().name),
                        argPtr));
                }
                argPtr += 4;
            }
        }

        for (VarDecl3 decl : body.getVariableDeclarations()) {
            if (allocation.isSpilled(decl.getId().getName())) {
                // body decl contains args as well
                if (!offsetTable.containsKey(decl.getId().getName())) {
                    offsetTable.put(decl.getId().getName(), offset);
                    offset -= 4;
                }

            }
            // dont need to init the rest to null
        }

        return sb.toString();
    }


    private String buildProlog(String entryLabel, Integer frameSize) {
        String prolog = String.format("%s:\n" +
                "    stmfd sp!, {fp, lr, v1, v2, v3, v4, v5}\n" +
                "    add fp, sp, #24\n"
            , entryLabel);

        if (frameSize > 0) {
            prolog += String.format("    sub sp, fp, #%d\n", frameSize);
        }

        prolog += buildStack(frameSize);

        return prolog;
    }

    private String buildEpilogue(String exitLabel) {
        return String.format("%s:\n" +
                "%s"+
                "    sub sp, fp, #24\n" +
                "    ldmfd sp!, {fp, pc, v1, v2, v3, v4, v5}\n",
            exitLabel, (isMain ? "    mov a1, #0\n" : "")); // i get some page fault error if i dont do this
    }
}
