package main.java.parsetree.statement;

import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.type.Environment;

public class PrintLineStatement extends Statement {
    private Expression expr;

    public PrintLineStatement(Expression expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "println(" + expr.toString() + ");";
    }

    @Override
    public boolean typeCheck(Environment env) {
        return true;
    }
}
