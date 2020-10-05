package main.java.parsetree.statement;

import java.util.LinkedList;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.type.Environment;

public class IfStatement extends Statement {

    private final Expression condition;
    private final LinkedList<Statement> ifBlock;
    private final LinkedList<Statement> elseBlock;

    public IfStatement(Expression condition, LinkedList<Statement> ifBlock, LinkedList<Statement> elseBlock) {
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return String.format("If (%s) {\n%s} else {\n%s}", condition.toString(),
            Helper.getInstance().indent(
                Helper.getInstance().join(ifBlock)),
                Helper.getInstance().indent(Helper.getInstance().join(elseBlock)));
    }

    @Override
    public boolean typeCheck(Environment env) {
        return false;
    }
}
