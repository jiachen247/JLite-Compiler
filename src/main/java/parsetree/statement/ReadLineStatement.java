package main.java.parsetree.statement;

import main.java.parsetree.shared.Id;

public class ReadLineStatement extends Statement {
    private Id id;

    public ReadLineStatement(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "readln(" + id.toString() + ");";
    }
}
