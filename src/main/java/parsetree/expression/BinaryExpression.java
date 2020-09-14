package main.java.parsetree.expression;

import main.java.parsetree.operator.BinaryOperator;

public class BinaryExpression extends Expression {

    private final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    public BinaryExpression(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](" + operator.toString() + ")";
    }
}
