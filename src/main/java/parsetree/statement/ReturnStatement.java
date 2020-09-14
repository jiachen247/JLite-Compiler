package main.java.ast.statement;


import main.java.ast.expression.Expression;

public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return " + expression.toString() + ";";
    }
}
