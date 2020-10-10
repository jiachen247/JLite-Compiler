package main.java.ir3.exp;

public class StringLiteral3 implements Const {
    private String val;

    @Override
    public String toString() {
        return val;
    }

    public String isVal() {
        return val;
    }

    public StringLiteral3(String val) {
        this.val = val;
    }
}
