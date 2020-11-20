package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.staticcheckers.type.BasicType;

public class NullLiteral3 implements Const {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String generateArm(String target) {
        return String.format("    mov %s, #0\n", target);
    }

    @Override
    public BasicType getType() {
        return BasicType.NULL_TYPE;
    }

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }
}
