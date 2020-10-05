package main.java.parsetree.statement;

import main.java.staticcheckers.type.Environment;

public class EmptyReturnStatement extends Statement {
    @Override
    public String toString() {
        return "return;";
    }

    @Override
    public boolean typeCheck(Environment env) {
        return true;
    }
}
