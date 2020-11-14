package main.java.ir3;

import java.util.List;

import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.Helper;

public class MdBody3 {
    private List<VarDecl3> variableDeclarations;
    private List<Stmt3> stmts;


    public MdBody3(List<VarDecl3> variableDeclarations, List<Stmt3> stmts) {
        this.variableDeclarations = variableDeclarations;
        this.stmts = stmts;

    }

    public Integer getVariableCount() {
        return variableDeclarations.size();
    }

    public String generateArm() {
        StringBuilder arm = new StringBuilder();

        for (Stmt3 stmt: stmts) {
            arm.append(stmt.generateArm());
        }
        return arm.toString();
    }

    @Override
    public String toString() {
        return Helper.getInstance().indent(
            Helper.getInstance().join(variableDeclarations))
            + "\n" +
            Helper.getInstance().indent(Helper.getInstance().join(stmts));
    }

}
