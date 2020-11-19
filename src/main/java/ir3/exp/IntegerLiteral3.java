package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.staticcheckers.type.BasicType;

public class IntegerLiteral3 implements Const {
    private int val;

    @Override
    public String toString() {
        return val + "";
    }

    public IntegerLiteral3(int val) {
        this.val = val;
    }

    @Override
    public String generateArm() {
        return String.format("    ldr a1, =#%d\n", val);
    }

    @Override
    public BasicType getType() {
        return BasicType.INT_TYPE;
    }

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }
}
