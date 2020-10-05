package main.java.parsetree.expression;


import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ParenthesizedExpression extends Expression {
    private Expression expression;

    public ParenthesizedExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }


    @Override
    public BasicType typeCheck(Environment env) {
        return expression.typeCheck(env);
    }
}
