package main.java.parsetree.operator;

public class MinusOperator extends BinaryOperator {
    public MinusOperator(int x, int y) {
        super(x, y, "-");
    }

    @Override
    public String generateArm() {
        return "    sub v1, v3, v2\n";
    }
}
