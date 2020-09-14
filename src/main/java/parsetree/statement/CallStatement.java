package main.java.ast.statement;

import main.java.ast.expression.CallExpression;

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
