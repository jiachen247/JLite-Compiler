package main.java.parsetree.operator;

public class GEQOperator extends BinaryOperator {
    public GEQOperator(int x, int y) {
        super(x, y, ">=");
    }

    @Override
    public String generateArm() {
        return "    mov a1, #0\n    cmp a2, a3\n    movge a1, #1\n";
    }
}
