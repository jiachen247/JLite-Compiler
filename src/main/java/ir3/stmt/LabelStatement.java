package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Label;
import main.java.ir3.exp.Id3;

public class LabelStatement implements Stmt3 {
    public Label getLabel() {
        return label;
    }

    private Label label;

    public LabelStatement(Label label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return String.format("Label %d:", label.getId());
    }

    @Override
    public String generateArm() {
        return label.generateArm();
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
