package main.java.ir3.stmt;

import java.util.List;

import main.java.ir3.Label;
import main.java.parsetree.MdBody;

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
