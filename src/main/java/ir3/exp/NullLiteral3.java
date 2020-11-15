package main.java.ir3.exp;

public class NullLiteral3 implements Const {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public String generateArm() {
        return "=_null";
    }
}
