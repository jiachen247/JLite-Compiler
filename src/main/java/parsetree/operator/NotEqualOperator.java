package main.java.parsetree.operator;

public class NotEqualOperator extends BinaryOperator {
    public NotEqualOperator(int x, int y) {
        super(x, y, "!=");
    }

    @Override
    public String generateArm() {
        return "    cmp v2, v3\n    mov v1, #1\n    movne v1, #1\n";
    }


}
