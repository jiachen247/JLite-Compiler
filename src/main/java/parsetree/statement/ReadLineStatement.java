package main.java.ast.statement;

import main.java.ast.common.Id;

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
