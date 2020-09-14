package main.java.parsetree.expression;


import main.java.parsetree.shared.Type;

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
