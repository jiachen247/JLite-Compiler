package main.java.parsetree.expression;

public class ConcatExpression extends Expression {
    public final Expression left;
    public final String right;

    public ConcatExpression(Expression left, String right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](++)";
    }
}
