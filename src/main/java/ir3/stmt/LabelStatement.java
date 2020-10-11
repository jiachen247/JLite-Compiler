package main.java.ir3.stmt;

import main.java.ir3.Label;

public class LabelStatement implements Stmt3 {
    private Label label;

    public LabelStatement(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("Label %d:", label.getId());
    }

}
