package main.java.ast.statement;


import main.java.ast.common.Id;
import main.java.ast.expression.Expression;

public class AssignmentStatement extends Statement {

    private final Id id;
    private final Expression expression;

    public AssignmentStatement(Id id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("%s = %s;", id, expression);
    }
}
