package main.java.parsetree.statement;


import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class AssignmentStatement extends Statement {

    private final Id id;
    private final Expression expression;

    public AssignmentStatement(int x, int y, Id id, Expression expression) {
        super(x, y);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("%s = %s;", id, expression);
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        System.out.println(toString());
        BasicType idType = env.lookup(id);
        BasicType expType = expression.typeCheck(env, errors);
        if (idType.equals(BasicType.ERROR_TYPE) || expType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (!idType.equals(expType)) {
            System.out.println("Failed to assign " + expType + " type to " + idType + "type");
            return BasicType.ERROR_TYPE;
        } else {
            return BasicType.VOID_TYPE;
        }
    }
}
