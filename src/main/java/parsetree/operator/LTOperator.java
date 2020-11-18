package main.java.parsetree.operator;

public class LTOperator extends BinaryOperator {
    public LTOperator(int x, int y) {
        super(x, y, "<");
    }

    @Override
    public String generateArm() {
        return "    mov a1, #0\n    cmp a2, a3\n    movlt a1, #1\n";
    }
}
