package main.java.ir3.stmt;

import java.util.List;

import main.java.arm.GlobalOffsetTable;
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
        if (exp instanceof Id3) {
            if (GlobalOffsetTable.getInstance().getAllocator().isOnRegister(((Id3) exp).getName())) {
                String target = GlobalOffsetTable.getInstance().getAllocation(((Id3) exp).getName());
                return String.format("    cmp %s, #1\n    beq %s\n", target, label.getName());
            }

        }
        return String.format("%s    cmp a1, #1\n    beq %s\n", exp.generateArm("a1"), label.getName());


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
