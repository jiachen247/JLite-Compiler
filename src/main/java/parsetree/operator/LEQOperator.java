package main.java.parsetree.operator;

public class LEQOperator extends BinaryOperator {
    public LEQOperator(int x, int y) {
        super(x, y, "<=");
    }

    @Override
    public String generateArm() {
        return "    mov a1, #0\n    cmp a2, a3\n    movle a1, #1\n";
    }
}
