package main.java.parsetree.expression;

import java.util.LinkedList;

import main.java.parsetree.shared.Helper;

public class CallExpression extends Expression {

    private final Expression callee;
    private final LinkedList<Expression> arguments;


    public CallExpression(Expression callee, LinkedList<Expression> arguments) {
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "[" + callee.toString() + "(" + Helper.getInstance().concat(arguments) + ")]";
    }

}
