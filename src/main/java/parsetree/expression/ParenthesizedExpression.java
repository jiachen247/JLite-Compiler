package main.java.parsetree.expression;


import java.util.List;

import main.java.ir3.exp.Exp3Result;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ParenthesizedExpression extends Expression {
    private Expression expression;
    private BasicType type;

    public ParenthesizedExpression(int x, int y, Expression expression) {
        super(x, y);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + expression.toString() + ")";
    }


    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        type = expression.typeCheck(env, errors);
        return type;
    }

    @Override
    public Exp3Result toIR() {
        return expression.toIR();
    }

    @Override
    public BasicType getType() {
        return type;
    }
}
