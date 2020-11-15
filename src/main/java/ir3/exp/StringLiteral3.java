package main.java.ir3.exp;

import main.java.arm.StringLabels;

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
    public String generateArm() {
        return String.format("=%s", label);
    }
}
