package main.java.parsetree.statement;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class EmptyReturnStatement extends Statement {
    @Override
    public String toString() {
        return "return;";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.NULL_TYPE;
    }
}
