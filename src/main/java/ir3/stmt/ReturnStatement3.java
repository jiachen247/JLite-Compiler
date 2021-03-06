package main.java.ir3.stmt;

import java.util.List;

import main.java.ir3.Program3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;
import main.java.staticcheckers.type.BasicType;

public class ReturnStatement3 implements Stmt3 {
    private Exp3 returnVal;
    private BasicType type;

    public ReturnStatement3(Exp3 returnVal, BasicType type) {
        this.returnVal = returnVal;
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(" Return %s;", returnVal);
    }

    @Override
    public String generateArm() {
        String result;

//        if (type.equals(BasicType.INT_TYPE)) {
//            if (returnVal instanceof IntegerLiteral3) {
//                result = String.format("#%s", returnVal);
//            } else {
//                // impl after lot
//                result = String.format("$%s", returnVal);
//            }
//
//        } else if (type.equals(BasicType.STRING_TYPE)) {
//            if (returnVal instanceof StringLiteral3) {
//                result = String.format("#%s", ((StringLiteral3) returnVal).getLabel());
//            } else {
//                // impl after lot
//                result = String.format("$%s", returnVal);
//            }
//        } else if (type.equals(BasicType.NULL_TYPE) || type.equals(BasicType.VOID_TYPE)) {
//            result = "";
//        } else if (type.equals(BasicType.BOOL_TYPE)) {
//            if (returnVal instanceof BoolLiteral3) {
//                result = ((BoolLiteral3) returnVal).isVal() ? "#1" : "#0";
//            } else {
//                // impl after lot
//                result = String.format("$%s", returnVal);
//            }
//        } else {
//            result = "";
//        }

//        String code = "";
//
//        if (result != null && !result.equals("")) {
//            code += String.format("    mov a1, %s\n", result);
//        }
//        code += String.format("    b %s_exit\n", Program3.getCurrentMethod());

        return String.format("%s    b %s_exit\n",
            returnVal.generateArm("a1"),
            Program3.getCurrentMethod());
    }

    @Override
    public List<Id3> getUses() {
        return returnVal.getUses();
    }

    @Override
    public Id3 getDef() {
        return null;
    }
}
