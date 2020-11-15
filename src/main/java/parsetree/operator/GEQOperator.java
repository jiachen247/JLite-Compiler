package main.java.parsetree.operator;

public class GEQOperator extends BinaryOperator {
    public GEQOperator(int x, int y) {
        super(x, y, ">=");
    }

    @Override
    public String generateArm() {
        return "    mov v1, #0\n    cmp v2, v3\n    movge v1, #1\n";
    }
}
