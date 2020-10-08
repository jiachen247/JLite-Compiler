package main.java.parsetree.expression;

import java.util.List;

import main.java.parsetree.operator.UnaryOperator;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryExpression(int x, int y, UnaryOperator operator, Expression expression) {
        super(x, y);

        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + operator.toString() + ")[" + expression.toString() + "]";
    }

    @Override
    public BasicType typeCheck(Environment env, List< CheckError > errors) {
        // negation and not
        return expression.typeCheck(env, errors);
    }
}
