package main.java.ir3.exp;


import main.java.parsetree.operator.BinaryOperator;
import main.java.staticcheckers.type.BasicType;

public class BinaryExpression3 implements Exp3 {
    private BinaryOperator operator;
    private Exp3 left;
    private Exp3 right;
    private BasicType type;

    public BinaryOperator getOperator() {
        return operator;
    }

    public BasicType getType() {
        return type;
    }

    public BinaryExpression3(BinaryOperator operator, Exp3 left, Exp3 right, BasicType type) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.type = type;
    }

    @Override
    public String toString() {
        if (type.equals(BasicType.STRING_TYPE)) {
            return String.format("%s %s %s", right.toString(), operator, left.toString());
        }
        return String.format("%s %s %s", left.toString(), operator, right.toString());
    }

    @Override
    public String generateArm() {
        String leftArm = left.generateArm();
        String rightArm = right.generateArm();
        String opArm = operator.generateArm();

        return String.format("%s" +
            "    mov v2, v1\n" +
            "%s" +
            "    mov v3, v1\n" +
            "%s", leftArm, rightArm, opArm);
    }
}
