package main.java.parsetree.expression;

import java.util.List;

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
}
