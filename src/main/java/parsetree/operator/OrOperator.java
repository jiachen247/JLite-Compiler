package main.java.parsetree.operator;

public class OrOperator extends BinaryOperator {
    public OrOperator(int x, int y) {
        super(x, y, "||");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    or %s, %s, %s\n", target, operand1, operand2);
    }
}
