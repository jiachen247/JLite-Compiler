package main.java.parsetree.operator;

public class GTOperator extends BinaryOperator {
    public GTOperator(int x, int y) {
        super(x, y, ">");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    mov %s, #0\n" +
            "    cmp %s, %s\n" +
            "    movgt %s, #1\n", target, operand1, operand2, target);
    }
}
