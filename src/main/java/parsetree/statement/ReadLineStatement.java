package main.java.parsetree.statement;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.Environment;

public class ReadLineStatement extends Statement {
    private Id id;

    public ReadLineStatement(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "readln(" + id.toString() + ");";
    }

    @Override
    public boolean typeCheck(Environment env) {
        return false;
    }
}
