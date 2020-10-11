package main.java.ir3.exp;

public class BoolLiteral3 implements Const {
    private boolean val;

    @Override
    public String toString() {
        return val + "";
    }

    public BoolLiteral3(boolean val) {
        this.val = val;
    }
}
