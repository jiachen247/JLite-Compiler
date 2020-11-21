package main.java.ir3.exp;


import java.util.ArrayList;
import java.util.List;

import main.java.arm.Allocation;
import main.java.arm.GlobalOffsetTable;
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
    public String generateArm(String target) {
        if (type.equals(BasicType.STRING_TYPE)) {
            return generateStringConcatenationArm(target);
        }

        // use a2 and a3 and scratch register
        String leftTarget = "a1";
        String rightTarget = "a2";
        String leftArm = "";
        String rightArm = "";

        if (left instanceof Id3 && GlobalOffsetTable.getInstance().getAllocator().isOnRegister(((Id3) left).getName())) {
            leftTarget = GlobalOffsetTable.getInstance().getAllocation(((Id3) left).getName());
        } else {
            leftArm = left.generateArm(leftTarget);
        }

        if (right instanceof Id3 && GlobalOffsetTable.getInstance().getAllocator().isOnRegister(((Id3) right).getName())) {
            rightTarget = GlobalOffsetTable.getInstance().getAllocation(((Id3) right).getName());
        } else {
            rightArm = right.generateArm(rightTarget);
        }

        String opArm = operator.generateArm(target, leftTarget, rightTarget);

        // int
        return String.format("%s%s%s", leftArm, rightArm, opArm);
    }

    private String generateStringConcatenationArm(String target) {
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

        String leftArm = left.generateArm("a2");
        String rightArm = right.generateArm("a1");
        return String.format(
            "%s%s%s    push {a1, a2}\n" +
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
                "    pop {a1, a2}\n" +
                "    bl strcat(PLT)\n%s%s",
            Allocation.pusha3a4,
            leftArm, rightArm, target.equals("a1") ? "" : String.format("    mov %s, a1\n", target),
            Allocation.popa3a4
        );

    }

}
