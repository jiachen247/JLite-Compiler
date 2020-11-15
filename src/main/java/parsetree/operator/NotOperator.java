package main.java.parsetree.operator;

public class NotOperator extends UnaryOperator {
    public NotOperator(int x, int y) {
        super(x, y, "!");
    }

    @Override
    public String generateArm() {
        // xor is called eor in arm
        return "    eor v1, v1, #1\n";
    }
}
