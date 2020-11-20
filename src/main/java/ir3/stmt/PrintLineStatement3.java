package main.java.ir3.stmt;

import java.util.List;

import main.java.arm.StringLabels;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;
import main.java.staticcheckers.type.BasicType;

public class PrintLineStatement3 implements Stmt3 {
    Exp3 expr;

    public PrintLineStatement3(Exp3 temp1) {
        expr = temp1;
    }

    @Override
    public String toString() {
        return " println(" + expr.toString() + ");";
    }

    @Override
    public String generateArm() {
        StringBuilder sb = new StringBuilder(expr.generateArm());
        if (expr.getType().equals(BasicType.BOOL_TYPE)) {
            sb.append(String.format("    cmp a1, #1\n" +
                    "    ldreq a1, =%s\n" +
                    "    ldrne a1, =%s\n",
                StringLabels.trueLabel, StringLabels.falseLabel));
        } else if (expr.getType().equals(BasicType.STRING_TYPE)) {
            sb.append("    mov a2, a1\n");
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.stringLabel));

        } else if (expr.getType().equals(BasicType.INT_TYPE)) {
            sb.append("    mov a2, a1\n");
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.intLabel));
        } else {
            // else null
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.nullLabel));
        }

        sb.append("    bl printf(PLT)\n");
        return sb.toString();

    }

    @Override
    public List<Id3> getUses() {
        return expr.getUses();
    }

    @Override
    public Id3 getDef() {
        return null;
    }
}
