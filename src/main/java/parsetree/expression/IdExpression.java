package main.java.parsetree.expression;

import java.util.List;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IdExpression extends Expression {

    public Id id;

    public IdExpression(int x, int y, Id id) {
        super(x, y);
        this.id = id;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return env.lookup(id);
    }
}
