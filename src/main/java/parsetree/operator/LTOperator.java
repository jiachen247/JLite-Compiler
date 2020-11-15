package main.java.parsetree.operator;

public class LTOperator extends BinaryOperator {
    public LTOperator(int x, int y) {
        super(x, y, "<");
    }

    @Override
    public String generateArm() {
        return "    mov v1, #0\n    cmp v2, v3\n    movlt v1, #1\n";
    }
}
