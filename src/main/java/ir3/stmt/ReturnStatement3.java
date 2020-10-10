package main.java.ir3.stmt;

import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Idc3;

public class ReturnStatement3 implements Stmt3 {
    private Exp3 returnVal;

    public ReturnStatement3(Exp3 returnVal) {
        this.returnVal = returnVal;
    }

    @Override
    public String toString() {
        return String.format(" Return %s;", returnVal);
    }
}
