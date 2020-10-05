package main.java.parsetree.statement;


import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.type.Environment;

public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return " + expression.toString() + ";";
    }

    @Override
    public boolean typeCheck(Environment env) {
        return false;
    }
}
