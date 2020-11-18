package main.java.parsetree.operator;

public class PlusOperator extends BinaryOperator {
    public PlusOperator(int x, int y) {
        super(x, y, "+");
    }

    @Override
    public String generateArm() {
        return "    add a1, a2, a3\n";
    }
}
