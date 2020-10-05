package main.java.parsetree.statement;

import main.java.parsetree.shared.Id;
import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class PropertyAssignmentStatement extends Statement {

    private final Expression object;
    private final Id property;
    private final Expression expression;

    public PropertyAssignmentStatement(Expression object, Id property, Expression expression) {
        this.object = object;
        this.property = property;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("%s.%s = %s", object, property, expression);
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.NULL_TYPE;
    }
}
