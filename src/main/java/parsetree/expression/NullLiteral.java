package main.java.parsetree.expression;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class NullLiteral extends Expression {

    @Override
    public String toString() {
        return "null";
    }


    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.NULL_TYPE;
    }
}
