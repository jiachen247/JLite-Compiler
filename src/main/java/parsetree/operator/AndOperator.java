package main.java.parsetree.operator;

public class AndOperator extends BinaryOperator {
    public AndOperator(int x, int y) {
        super(x, y, "&&");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    and %s, %s, %s\n", target, operand1, operand2);
    }
}
