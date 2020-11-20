package main.java.arm;

import java.util.ArrayList;

import main.java.ir3.stmt.Stmt3;

public class CFGNode {
    public Integer getIndex() {
        return index;
    }

    private Integer index;

    public Stmt3 getStmt() {
        return stmt;
    }

    private Stmt3 stmt;

    public ArrayList<CFGNode> getNext() {
        return next;
    }

    private ArrayList<CFGNode> next;

    public CFGNode(Stmt3 stmt, int index) {
        this.index = index;
        this.stmt = stmt;
        this.next = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("  %d -> ", index));
        for (CFGNode neig : next) {
            sb.append(neig.index + ",");
        }
        sb.append("\n");

        for (CFGNode neig : next) {
            sb.append(neig.toString());
            sb.append("\n");
        }

        return sb.toString();
    }

    public void addNext(CFGNode node) {
        if (node != null) {
            next.add(node);
        }
    }
}
