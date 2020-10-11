package main.java.ir3.stmt;

import main.java.ir3.Label;

public class GotoStatement3 implements Stmt3 {
    private Label label;

    public GotoStatement3(Label label) {

        this.label = label;
    }

    @Override
    public String toString() {
        return String.format(" goto %s;", label.toString());
    }
}
