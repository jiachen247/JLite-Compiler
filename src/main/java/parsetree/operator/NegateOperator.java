package main.java.parsetree.operator;

public class NegateOperator extends UnaryOperator {
    public NegateOperator(int x, int y) {
        super(x, y, "-");
    }
}
