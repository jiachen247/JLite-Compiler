package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.NullLiteral3;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class NullLiteral extends Expression {

    public NullLiteral(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "null";
    }


    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return BasicType.NULL_TYPE;
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new NullLiteral3());
    }

    @Override
    public BasicType getType() {
        return BasicType.NULL_TYPE;
    }
}
