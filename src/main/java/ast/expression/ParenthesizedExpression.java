package main.java.ast.expression;


public class ParenthesizedExpression extends Expression {
    private Expression expression;

    public ParenthesizedExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }

}
