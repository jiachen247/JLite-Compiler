package main.java.parsetree.operator;

public class EqualOperator extends BinaryOperator {
    public EqualOperator(int x, int y) {
        super(x, y, "==");
    }

    @Override
    public String generateArm() {
        return "    mov a1, #0\n    cmp a2, a3\n    moveq a1, #1\n";
    }
}
