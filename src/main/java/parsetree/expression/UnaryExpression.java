package main.java.parsetree.expression;

import main.java.parsetree.operator.UnaryOperator;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryExpression(UnaryOperator operator, Expression expression) {

        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + operator.toString() + ")[" + expression.toString() + "]";
    }
}
