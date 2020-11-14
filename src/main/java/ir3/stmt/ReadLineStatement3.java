package main.java.ir3.stmt;

import main.java.parsetree.shared.Id;

public class ReadLineStatement3 implements Stmt3 {
    private Id id;

    public ReadLineStatement3(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return " readln(" + id.toString() + ");";
    }

    @Override
    public String generateArm() {
        // readln is not implemented as per assignment specification
        return "readln\n";
    }
}
