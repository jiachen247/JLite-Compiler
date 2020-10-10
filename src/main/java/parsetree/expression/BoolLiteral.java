package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Result;
import main.java.ir3.exp.BoolLiteral3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.StringLiteral3;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class BoolLiteral extends Expression {

    public final boolean value;

    public BoolLiteral(int x, int y, boolean value) {
        super(x, y);
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return BasicType.BOOL_TYPE;
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new BoolLiteral3(value));
    }

}
