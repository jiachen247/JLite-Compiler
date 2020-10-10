package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Result;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.IntegerLiteral3;
import main.java.ir3.exp.StringLiteral3;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IntLiteral extends Expression {
    public final int value;

    public IntLiteral(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }


    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return BasicType.INT_TYPE;
    }


    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new IntegerLiteral3(value));
    }

}
