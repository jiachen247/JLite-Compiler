package main.java.ir3.exp;


import java.util.ArrayList;
import java.util.List;

import main.java.parsetree.operator.BinaryOperator;
import main.java.staticcheckers.type.BasicType;

public class BinaryExpression3 implements Exp3 {
    private BinaryOperator operator;
    private Exp3 left;
    private Exp3 right;
    private BasicType type;
    private List<Id3> uses;

    public BinaryOperator getOperator() {
        return operator;
    }

    public BasicType getType() {
        return type;
    }

    @Override
    public List<Id3> getUses() {
        return uses;
    }

    public BinaryExpression3(BinaryOperator operator, Exp3 left, Exp3 right, BasicType type) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.type = type;

        this.uses = new ArrayList<>();
        this.uses.addAll(left.getUses());
        this.uses.addAll(right.getUses());
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", right.toString(), operator, left.toString());
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
            "    mov a2, a1\n" +
            "%s" +
            "    mov a3, a1\n" +
            "%s", leftArm, rightArm, opArm);
    }

    private String generateStringConcatenationArm() {
        /*
            String a, b;

            push {b, a}
            t1 = strlen(a);
            push {t1}
            t2 = strlen(b)
            pop a2
            t1 += a2
            t3 = malloc(t1)
            pop a2
            strcpy(t3, a2)
            pop a2
            strcat(t3, a2)
            return t3
         */

        String leftArm = left.generateArm();
        String rightArm = right.generateArm();
        return String.format(
            "%s    mov a2, a1\n%s    push {a1, a2}\n" +
                "    push {a2}\n" +
                "    bl strlen(PLT)\n" +
                "    pop {a2}\n" +
                "    push { a1 }\n" +
                "    mov a1, a2\n" +
                "    bl strlen(PLT)\n" +
                "    pop {a2}\n" +
                "    add a1, a1, a2\n" +
                "    bl malloc(PLT)\n" +
                "    pop {a2}\n" +
                "    push {a1}\n" +
                "    bl strcpy(PLT)\n" +
                "    pop {a2, a1}\n" +
                "    bl strcat(PLT)\n",
            leftArm, rightArm
        );
    }


}
