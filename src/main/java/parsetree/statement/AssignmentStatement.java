package main.java.parsetree.statement;


import main.java.parsetree.shared.Id;
import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.type.Environment;

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

    @Override
    public boolean typeCheck(Environment env) {
        return true;
    }
}
