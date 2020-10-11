package main.java.parsetree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ir3.MdBody3;
import main.java.ir3.VarDecl3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.VarDecl;
import main.java.parsetree.statement.Statement;

public class MdBody extends Node {

    public final LinkedList<VarDecl> variableDeclarations;
    public final LinkedList<Statement> stmts;


    public MdBody(int x, int y, LinkedList<VarDecl> variableDeclarations, LinkedList<Statement> stmts) {
        super(x, y);
        this.variableDeclarations = variableDeclarations;
        this.stmts = stmts;
    }

    @Override
    public String toString() {
        return Helper.getInstance().indent(
            Helper.getInstance().join(variableDeclarations)) +
            Helper.getInstance().indent(Helper.getInstance().join(stmts));
    }

    public MdBody3 toMdBody3() {
        List<VarDecl3> temps = new ArrayList<>();
        List<Stmt3> stmts3 = new ArrayList<>();

        for (Statement stmt : stmts) {
            // join all stmts new stmts tgt
            Stmt3Result result = stmt.toIR();
            temps.addAll(result.getTempVars());
            stmts3.addAll(result.getStmt3List());
        }

        List<VarDecl3> fullVars = variableDeclarations.stream().map(VarDecl3::new).collect(Collectors.toList());
        fullVars.addAll(temps);

        return new MdBody3(fullVars, stmts3);
    }
}
