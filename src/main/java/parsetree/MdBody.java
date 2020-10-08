package main.java.parsetree;

import java.util.LinkedList;

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
}
