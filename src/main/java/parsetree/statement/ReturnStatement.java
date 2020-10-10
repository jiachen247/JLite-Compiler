package main.java.parsetree.statement;


import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ReturnStatement extends Statement {

    private Expression expression;

    public ReturnStatement(int x, int y, Expression expression) {
        super(x, y);
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "Return " + expression.toString() + ";";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType actual = expression.typeCheck(env, errors);
        BasicType expected = env.getLocalEnvironment().returnType;

        if (actual.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (actual.equals(expected)) {
            return actual;
        } else if (!expected.isPrimitiveType() && actual.equals(BasicType.NULL_TYPE)) {
            return actual;
        } else {
            errors.add(TypeChecker.buildTypeError(expression.x, expression.y,
                String.format("Expected function return type to be `%s` found `%s` instead.",
                    expected, actual)));
            return BasicType.ERROR_TYPE;
        }
    }
}
