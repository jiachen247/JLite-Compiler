package main.java.parsetree.expression;

import main.java.parsetree.shared.Id;

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
