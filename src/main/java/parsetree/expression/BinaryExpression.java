package main.java.parsetree.expression;


import main.java.parsetree.shared.Operator;

public class BinaryExpression extends Expression {

    private final Operator.Binary operator;
    public final Expression left;
    public final Expression right;

    public BinaryExpression(Operator.Binary operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](" + operator.toString() + ")";
    }
}
