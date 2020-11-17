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
        if (type.equals(BasicType.STRING_TYPE)) {
            return generateStringConcatenationArm();
        }

        String leftArm = left.generateArm();
        String rightArm = right.generateArm();
        String opArm = operator.generateArm();


        // int
        return String.format("%s" +
            "    mov v2, v1\n" +
            "%s" +
            "    mov v3, v1\n" +
            "%s", leftArm, rightArm, opArm);
    }

    private String generateStringConcatenationArm() {
        /*
            String a, b;

            t1 = strlen(a);
            t2 = strlen(b)
            t3 = malloc(t1 + t2)
            strcpy(t3, a)
            strcat(t3, b)
            return t3
         */

        String leftArm = left.generateArm();
        String rightArm = right.generateArm();

        return String.format(
            "%s    mov v2, v1\n" +
                "%s    mov v3, v1\n" +
                "    mov a1, v2\n" +
                "    bl strlen(PLT)\n" +
                "    mov v1, a1\n" +
                "    mov a1, v3\n" +
                "    bl strlen(PLT)\n" +
                "    add a1, a1, v1\n" +
                "    bl malloc(PLT)\n" +
                "    mov v1, a1\n" +
                "    mov a2, v3\n" +
                "    bl strcpy(PLT)\n" +
                "    mov a1, v1\n" +
                "    mov a2, v2\n" +
                "    bl strcat(PLT)\n" +
                "    mov v3, a1\n",
            leftArm, rightArm
        );
    }


}
