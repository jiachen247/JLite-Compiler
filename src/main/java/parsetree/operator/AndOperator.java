package main.java.parsetree.operator;

public class AndOperator extends BinaryOperator {
    public AndOperator(int x, int y) {
        super(x, y, "&&");
    }

    @Override
    public String generateArm() {
        return "    and a1, a2, a3\n";
    }
}
