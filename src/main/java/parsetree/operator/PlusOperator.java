package main.java.parsetree.operator;

public class PlusOperator extends BinaryOperator {
    public PlusOperator(int x, int y) {
        super(x, y, "+");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    add %s, %s, %s\n", target, operand1, operand2);
    }
}
