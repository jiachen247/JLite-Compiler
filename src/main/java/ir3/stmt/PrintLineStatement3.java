package main.java.ir3.stmt;

import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;

public class PrintLineStatement3 implements Stmt3 {
    Exp3 expr;

    public PrintLineStatement3(Exp3 temp1) {
        expr = temp1;
    }

    @Override
    public String toString() {
        return "println(" + expr.toString() + ");";
    }
}
