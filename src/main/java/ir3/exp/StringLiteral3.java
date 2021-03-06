package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.arm.StringLabels;
import main.java.staticcheckers.type.BasicType;

public class StringLiteral3 implements Const {
    private String val;
    private String label;

    @Override
    public String toString() {
        if (val == null || val.equals("")) {
            return "null";
        }

        return "\"" + val + "\"";
    }

    public StringLiteral3(String val) {
        this.val = val;
        this.label = StringLabels.getInstance().getLabel(val);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String generateArm(String target) {
        return String.format("    ldr %s, =%s\n", target, label);
    }

    @Override
    public BasicType getType() {
        return BasicType.STRING_TYPE;
    }

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }
}
