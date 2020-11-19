package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Program3;
import main.java.ir3.exp.Id3;

public class EmptyReturnStatement3 implements Stmt3 {
    @Override
    public String toString() {
        return " Return ;";
    }

    @Override
    public String generateArm() {
        return String.format("    b %s_exit\n", Program3.getCurrentMethod());
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
