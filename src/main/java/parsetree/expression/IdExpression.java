package main.java.parsetree.expression;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IdExpression extends Expression {

    public Id id;

    public IdExpression(Id id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return env.lookup(id);
    }
}
