package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.exp.Id3;
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

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }

    @Override
    public Id3 getDef() {
        return null;
    }
}
