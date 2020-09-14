package main.java.ast.expression;

import main.java.ast.common.Id;

public class IdExpression extends Expression {

    public Id id;

    public IdExpression(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
