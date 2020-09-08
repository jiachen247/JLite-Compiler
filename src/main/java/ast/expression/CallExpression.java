package main.java.ast.expression;

import java.util.LinkedList;

import main.java.ast.common.Util;

public class CallExpression extends Expression {

    private final Expression callee;
    private final LinkedList<Expression> arguments;


    public CallExpression(Expression callee, LinkedList<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "[" + callee.toString() + "(" + Util.listToString(arguments) + ")]";
    }

}
