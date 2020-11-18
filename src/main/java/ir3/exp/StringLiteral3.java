package main.java.ir3.exp;

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
    public String generateArm() {
        return String.format("    ldr a1, =%s\n", label);
    }

    @Override
    public BasicType getType() {
        return BasicType.STRING_TYPE;
    }
}
