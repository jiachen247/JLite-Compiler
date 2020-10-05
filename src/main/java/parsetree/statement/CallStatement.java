package main.java.parsetree.statement;

import main.java.parsetree.expression.CallExpression;
import main.java.staticcheckers.type.Environment;

public class CallStatement extends Statement {

    private CallExpression callExpression;

    public CallStatement(CallExpression callExpression) {
        this.callExpression = callExpression;
    }

    @Override
    public String toString() {
        return callExpression.toString() + ";";
    }

    @Override
    public boolean typeCheck(Environment env) {
        return true;
    }
}
