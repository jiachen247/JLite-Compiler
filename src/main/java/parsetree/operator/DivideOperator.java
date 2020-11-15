package main.java.parsetree.operator;

public class DivideOperator extends BinaryOperator {
    public DivideOperator(int x, int y) {
        super(x, y, "/");
    }

    @Override
    public String generateArm() {
        return "undefined";
    }
}
