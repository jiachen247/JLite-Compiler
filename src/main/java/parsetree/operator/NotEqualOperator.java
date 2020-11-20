package main.java.parsetree.operator;

public class NotEqualOperator extends BinaryOperator {
    public NotEqualOperator(int x, int y) {
        super(x, y, "!=");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    mov %s, #0\n" +
            "    cmp %s, %s\n" +
            "    movne %s, #1\n", target, operand1, operand2, target);
    }


}
