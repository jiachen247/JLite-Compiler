package main.java.parsetree.operator;

public class OrOperator extends BinaryOperator {
    public OrOperator(int x, int y) {
        super(x, y, "||");
    }

    @Override
    public String generateArm() {
        return "    or v1, v2, v3\n";
    }
}
