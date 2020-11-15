package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ThisExpression extends Expression {
    private BasicType type;

    public ThisExpression(int x, int y) {
        super(x, y);
    }


    @Override
    public String toString() {
        return "this";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        type = env.getClassContext().getCname();
        return type;
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new Id3("this", type));
    }

    @Override
    public BasicType getType() {
        return type;
    }
}
