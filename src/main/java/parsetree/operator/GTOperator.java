package main.java.parsetree.operator;

public class GTOperator extends BinaryOperator {
    public GTOperator(int x, int y) {
        super(x, y, ">");
    }

    @Override
    public String generateArm() {
        return "    mov a1, #0\n    cmp a2, a3\n    movgt a1, #1\n";
    }
}
