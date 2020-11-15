package main.java.parsetree.operator;

public class LEQOperator extends BinaryOperator {
    public LEQOperator(int x, int y) {
        super(x, y, "<=");
    }

    @Override
    public String generateArm() {
        return "    mov v1, #0\n    cmp v2, v3\n    movle v1, #1\n";
    }
}
