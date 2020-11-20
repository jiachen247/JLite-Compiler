package main.java.parsetree.operator;

public class MinusOperator extends BinaryOperator {
    public MinusOperator(int x, int y) {
        super(x, y, "-");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    sub %s, %s, %s\n", target, operand2, operand1);
    }
}
