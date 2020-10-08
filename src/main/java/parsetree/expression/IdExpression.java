package main.java.parsetree.expression;

import java.util.List;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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
        BasicType basicType = env.lookup(id);
        if (basicType.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        }

        return basicType;
    }
}
