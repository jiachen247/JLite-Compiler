package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

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
    public String generateArm(String target) {
        return val ? "    mov a1, #1\n" : "    mov a1, #0\n";
    }

    @Override
    public BasicType getType() {
        return BasicType.BOOL_TYPE;
    }

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }
}
