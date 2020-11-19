package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Label;
import main.java.ir3.exp.Id3;

public class GotoStatement3 implements Stmt3 {
    public Label getLabel() {
        return label;
    }

    private Label label;

    public GotoStatement3(Label label) {

        this.label = label;
    }

    @Override
    public String toString() {
        return String.format(" goto %s;", label.toString());
    }

    @Override
    public String generateArm() {
        return String.format("    b %s\n", label.getName());
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
