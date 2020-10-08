package main.java.parsetree.statement;


import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
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
            // todo handle null
            return actual;
        } else {
            System.out.println("Return type mismatched. Expected " + expected + " but got " + actual);
            return BasicType.ERROR_TYPE;
        }
    }
}
