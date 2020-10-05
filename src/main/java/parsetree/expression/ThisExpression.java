package main.java.parsetree.expression;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ThisExpression extends Expression {
    @Override
    public String toString() {
        return "this";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return env.getClassContext().getCname();
    }
}
