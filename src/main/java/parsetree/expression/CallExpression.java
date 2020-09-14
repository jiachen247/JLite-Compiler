package main.java.ast.expression;

import java.util.LinkedList;

public class CallExpression extends Expression {

    private final Expression callee;
    private final LinkedList<Expression> arguments;


    public CallExpression(Expression callee, LinkedList<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "[" + callee.toString() + "(" + main.java.ast.common.Helper.concat(arguments) + ")]";
    }

}
