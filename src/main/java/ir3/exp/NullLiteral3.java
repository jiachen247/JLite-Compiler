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
    public String generateArm() {
        return "    ldr a1, =_null\n";
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
