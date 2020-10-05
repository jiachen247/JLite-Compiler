package main.java.parsetree.expression;


import main.java.parsetree.shared.Type;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class NewExpression extends Expression {

    private Type type;

    public NewExpression(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "new " + type.toString() + "()";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.fromType(type);
    }
}
