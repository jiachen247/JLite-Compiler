package main.java.parsetree.expression;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IntLiteral extends Expression {
    public final int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }


    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.INT_TYPE;
    }
}
