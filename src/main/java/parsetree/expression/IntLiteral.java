package main.java.parsetree.expression;

import java.util.List;

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
}
