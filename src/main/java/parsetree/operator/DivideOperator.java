package main.java.parsetree.operator;

public class DivideOperator extends BinaryOperator {
    public DivideOperator(int x, int y) {
        super(x, y, "/");
    }

    @Override
    public String generateArm(String target, String operand1, String operand2) {
        return "undefined";
    }
}
