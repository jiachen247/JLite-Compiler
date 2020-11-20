package main.java.parsetree.operator;

public class TimesOperator extends BinaryOperator {
    public TimesOperator(int x, int y) {
        super(x, y, "*");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return String.format("    mul %s, %s, %s\n", target, operand1, operand2);
    }
}
