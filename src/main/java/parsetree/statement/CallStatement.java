package main.java.parsetree.statement;

import main.java.parsetree.expression.CallExpression;

public class CallStatement extends Statement {

    private CallExpression callExpression;

    public CallStatement(CallExpression callExpression) {
        this.callExpression = callExpression;
    }

    @Override
    public String toString() {
        return callExpression.toString() + ";";
    }

}
