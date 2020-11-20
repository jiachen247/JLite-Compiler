package main.java.parsetree.operator;

public abstract class UnaryOperator extends Operator {
    public UnaryOperator(int x, int y, String operation) {
        super(x, y, operation);
    }
    public abstract String generateArm(String target);
}
