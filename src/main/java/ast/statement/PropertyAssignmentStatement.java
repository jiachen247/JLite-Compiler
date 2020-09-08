package main.java.ast.statement;

import main.java.ast.common.Id;
import main.java.ast.expression.Expression;

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
}
