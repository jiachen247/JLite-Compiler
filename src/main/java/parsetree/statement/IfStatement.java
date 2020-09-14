package main.java.ast.statement;

import java.util.LinkedList;

import main.java.ast.expression.Expression;

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
            main.java.ast.common.Helper.indent(main.java.ast.common.Helper.join(ifBlock)), main.java.ast.common.Helper.indent(main.java.ast.common.Helper.join(elseBlock)));
    }
}
