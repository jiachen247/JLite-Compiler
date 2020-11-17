package main.java.ir3.exp;

import main.java.staticcheckers.type.BasicType;

public class NullLiteral3 implements Const {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String generateArm() {
        return "    ldr v1, =_null\n";
    }

    @Override
    public BasicType getType() {
        return BasicType.NULL_TYPE;
    }
}
