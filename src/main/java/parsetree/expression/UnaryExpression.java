package main.java.ast.expression;


import main.java.ast.common.Operator;

public class UnaryExpression extends Expression {

    private final Operator.Unary operator;
    private final Expression expression;

    public UnaryExpression(Operator.Unary operator, Expression expression) {

        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + operator.toString() + ")[" + expression.toString() + "]";
    }
}
