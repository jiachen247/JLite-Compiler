package main.java.parsetree.operator;

public class NegateOperator extends UnaryOperator {
    public NegateOperator(int x, int y) {
        super(x, y, "-");
    }

    @Override
    public String generateArm(String target) {
        return String.format("    mul %s, %s, #-1\n", target, target);
    }
}
