package main.java.ast.expression;


import main.java.ast.common.Id;

public class InExpression extends Expression {

    public final Expression object;
    public final Id property;

    public InExpression(Expression object, Id property) {
        this.object = object;
        this.property = property;
    }

    @Override
    public String toString() {
        return "[" + object.toString() + "." + property.toString() + "]";
    }

}
