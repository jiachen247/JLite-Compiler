package main.java.parsetree.expression;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class BoolLiteral extends Expression {

    public final boolean value;

    public BoolLiteral(boolean value) {

        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.BOOL_TYPE;
    }
}
