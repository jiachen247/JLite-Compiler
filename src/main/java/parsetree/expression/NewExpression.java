package main.java.ast.expression;


import main.java.ast.common.Type;

public class NewExpression extends Expression {

    private Type type;

    public NewExpression(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "new " + type.toString() + "()";
    }

}
