package main.java.parsetree.operator;

public class PlusOperator extends BinaryOperator {
    public PlusOperator(int x, int y) {
        super(x, y, "+");
    }

    @Override
    public String generateArm() {
        return "    add v1, v2, v3\n";
    }
}
