package main.java.ir3.stmt;

import main.java.ir3.Label;
import main.java.ir3.exp.Exp3;

public class IfStatement3 implements Stmt3 {
    private Exp3 exp;
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
        return "todo impl if";
    }
}
