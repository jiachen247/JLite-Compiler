package main.java.ir3.exp;

import main.java.staticcheckers.type.BasicType;

public class BoolLiteral3 implements Const {
    public boolean isVal() {
        return val;
    }

    private boolean val;

    @Override
    public String toString() {
        return val + "";
    }

    public BoolLiteral3(boolean val) {
        this.val = val;
    }


    @Override
    public String generateArm() {
        return val ? "    mov v1, #1\n" : "    mov v1, #0\n";
    }

    @Override
    public BasicType getType() {
        return BasicType.BOOL_TYPE;
    }
}
