package main.java.parsetree.operator;

public class TimesOperator extends BinaryOperator {
    public TimesOperator(int x, int y) {
        super(x, y, "*");
    }

    @Override
    public String generateArm() {
        return "    mul v1, v2, v3\n";
    }
}
