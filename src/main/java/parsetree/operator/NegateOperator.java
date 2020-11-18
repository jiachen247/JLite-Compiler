package main.java.parsetree.operator;

public class NegateOperator extends UnaryOperator {
    public NegateOperator(int x, int y) {
        super(x, y, "-");
    }

    @Override
    public String generateArm() {
        return "    mul a1, a2, #-1\n";
    }
}
