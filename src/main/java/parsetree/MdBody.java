package main.java.ast;

import java.util.LinkedList;

import main.java.ast.common.Helper;
import main.java.ast.common.VarDecl;
import main.java.ast.statement.Statement;

public class MdBody implements Node {

    public final LinkedList<VarDecl> variableDeclarations;
    public final LinkedList<Statement> stmts;

    public MdBody(LinkedList<VarDecl> variableDeclarations, LinkedList<Statement> stmts) {
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
