package main.java.parsetree.operator;

public abstract class BinaryOperator extends Operator {
    public BinaryOperator(int x, int y, String operation) {
        super(x, y, operation);
    }
}
