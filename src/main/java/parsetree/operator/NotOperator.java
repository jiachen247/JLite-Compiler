package main.java.parsetree.operator;

public class NotOperator extends UnaryOperator {
    public NotOperator(int x, int y) {
        super(x, y, "!");
    }

    @Override
    public String generateArm(String target) {
        return String.format("    eor %s, %s, #-1\n", target, target);
    }
}
