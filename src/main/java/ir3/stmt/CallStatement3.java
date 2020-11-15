package main.java.ir3.stmt;

import main.java.ir3.exp.CallExpression3;
import main.java.ir3.exp.Exp3;

public class CallStatement3 implements Stmt3 {
    private Exp3 callExpression;

    public CallStatement3(Exp3 callExpression) {
        this.callExpression = callExpression;
    }

    @Override
    public String toString() {
        return String.format(" %s;", callExpression);
    }

    @Override
    public String generateArm() {
        return String.format("%s\n", ((CallExpression3) callExpression).generateArm());
    }
}
