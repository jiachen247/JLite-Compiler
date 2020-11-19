package main.java.ir3.stmt;

import java.util.List;

import main.java.ir3.Label;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;

public class IfStatement3 implements Stmt3 {
    private Exp3 exp;

    public Label getLabel() {
        return label;
    }

    private Label label;

    public IfStatement3(Exp3 exp, Label label) {
        this.exp = exp;
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format(" If (%s) goto %s;", exp.toString(), label.toString());
    }

    @Override
    public String generateArm() {
        return String.format("%s    cmp a1, #1\n    beq %s\n", exp.generateArm(), label.getName());
    }

    @Override
    public List<Id3> getUses() {
        return exp.getUses();
    }

    @Override
    public Id3 getDef() {
        return null;
    }
}
