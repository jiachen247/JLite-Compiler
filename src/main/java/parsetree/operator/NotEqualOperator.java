package main.java.parsetree.operator;

public class NotEqualOperator extends BinaryOperator {
    public NotEqualOperator(int x, int y) {
        super(x, y, "!=");
    }

    @Override
    public String generateArm() {
        return "    cmp a2, a3\n    mov a1, #1\n    movne a1, #1\n";
    }


}
