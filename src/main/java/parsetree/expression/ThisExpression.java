package main.java.parsetree.expression;

import java.util.List;

import main.java.ir3.Result;
import main.java.ir3.exp.Exp3Result;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ThisExpression extends Expression {
    public ThisExpression(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "this";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return env.getClassContext().getCname();
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result();
    }
}
