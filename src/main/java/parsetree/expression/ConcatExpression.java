package main.java.parsetree.expression;

public class ConcatExpression extends Expression {
    public final Expression left;
    public final Expression right;

    public ConcatExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](++)";
    }
}
