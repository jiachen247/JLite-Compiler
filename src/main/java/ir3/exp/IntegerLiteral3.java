package main.java.ir3.exp;

public class IntegerLiteral3 implements Const {
    private int val;

    @Override
    public String toString() {
        return val + "";
    }

    public int isVal() {
        return val;
    }

    public IntegerLiteral3(int val) {
        this.val = val;
    }
}
