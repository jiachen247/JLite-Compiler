package main.java.parsetree.operator;

public class EqualOperator extends BinaryOperator {
    public EqualOperator(int x, int y) {
        super(x, y, "==");
    }

    @Override
    public String generateArm() {
        return "    mov v1, #0\n    cmp v2, v3\n    moveq v1, #1\n";
    }
}
