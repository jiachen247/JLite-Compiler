package main.java.parsetree.statement;


import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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
        BasicType idType = env.lookup(id);
        if (idType.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        }

        BasicType expType = expression.typeCheck(env, errors);

        if (idType.equals(BasicType.ERROR_TYPE) || expType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (!idType.equals(expType)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Failed to assign `%s` to variable of type `%s`.", idType, expType)));
            return BasicType.ERROR_TYPE;
        } else {
            return BasicType.VOID_TYPE;
        }
    }
}
