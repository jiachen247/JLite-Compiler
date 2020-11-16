package main.java.ir3.stmt;

import main.java.arm.StringLabels;
import main.java.ir3.exp.BoolLiteral3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.exp.IntegerLiteral3;
import main.java.ir3.exp.StringLiteral3;
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
        if (expr instanceof Id3) {
            Id3 id = (Id3) expr;
            if (id.getType().equals(BasicType.BOOL_TYPE)) {
                sb.append(String.format("    cmp v1, #1\n" +
                        "    ldreq a1, =%s\n" +
                        "    ldrne a1, =%s\n",
                    StringLabels.trueLabel, StringLabels.falseLabel));
            } else if (id.getType().equals(BasicType.STRING_TYPE)) {
                sb.append("    mov a2, v1\n");
                sb.append(String.format("    ldr a1, =%s\n", StringLabels.stringLabel));

            } else if (id.getType().equals(BasicType.INT_TYPE)) {
                sb.append("    mov a2, v1\n");
                sb.append(String.format("    ldr a1, =%s\n", StringLabels.intLabel));
            } else {
                // else null
                sb.append(String.format("    ldr a1, =%s\n", StringLabels.nullLabel));
            }

        } else if (expr instanceof BoolLiteral3) {
            sb.append(String.format("    cmp v1, #1\n" +
                    "    ldreq a1, =%s\n" +
                    "    ldrne a1, =%s\n",
                StringLabels.trueLabel, StringLabels.falseLabel));
        } else if (expr instanceof StringLiteral3) {
            sb.append("    mov a2, v1\n");
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.stringLabel));

        } else if (expr instanceof IntegerLiteral3) {
            sb.append("    mov a2, v1\n");
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.intLabel));
        } else {
            // else null
            sb.append(String.format("    ldr a1, =%s\n", StringLabels.nullLabel));
        }

        sb.append("    bl printf(PLT)\n");
        return sb.toString();

    }

//    @Override
//    public String generateArm() {
//
//        StringBuilder sb = new StringBuilder();
//
//        // According to typing rules we only need to support, Integers, Bools and Strings.
//        if (expr instanceof BoolLiteral3) {
//            if (((BoolLiteral3) expr).isVal()) {
//                sb.append(String.format("    ldr a1, =%s\n", StringLabels.trueLabel));
//            } else {
//                sb.append(String.format("    ldr a1, =%s\n", StringLabels.falseLabel));
//            }
//        } else if (expr instanceof StringLiteral3) {
//            sb.append(String.format("    ldr a1, =%s\n", StringLabels.stringLabel));
//            sb.append(String.format("    ldr a2, =%s\n", ((StringLiteral3) expr).getLabel()));
//        } else if (expr instanceof IntegerLiteral3) {
//            sb.append(String.format("    ldr a1, =%s\n", StringLabels.intLabel));
//            sb.append(String.format("    mov a2, #%s\n", expr.toString()));
//        } else if (expr instanceof NullLiteral) {
//            sb.append(String.format("    ldr a1, =%s\n", StringLabels.nullLabel));
//        } else if (expr instanceof Id3) {
////            System.out.println(" handle ids");
////            sb.append("please impl");
//        } else {
//            //System.out.println("error : unsupported println statmeent!");
//        }
//
//        sb.append("    bl printf(PLT)\n");
//        //check for other cases
//        return sb.toString();
//    }
}
