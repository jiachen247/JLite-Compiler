package main.java.parsetree.operator;

public class MinusOperator extends BinaryOperator {
    public MinusOperator(int x, int y) {
        super(x, y, "-");
    }

    @Override
    public String generateArm() {
        return "    sub a1, a3, a2\n";
    }
}
