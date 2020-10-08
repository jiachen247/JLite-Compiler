package main.java.parsetree.expression;

import java.util.List;

import main.java.parsetree.operator.UnaryOperator;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class UnaryExpression extends Expression {

    private final UnaryOperator operator;
    private final Expression expression;

    public UnaryExpression(int x, int y, UnaryOperator operator, Expression expression) {
        super(x, y);

        this.operator = operator;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "(" + operator.toString() + ")[" + expression.toString() + "]";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType basicType = expression.typeCheck(env, errors);

        if (basicType.equals(BasicType.INT_TYPE) && operator.getOperation().equals("-")) {
            return BasicType.INT_TYPE;
        } else if (basicType.equals(BasicType.BOOL_TYPE) && operator.getOperation().equals("!")) {
            return BasicType.BOOL_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(operator.x, operator.y,
                String.format("Type `[%s](%s)` not supported for Unary Expressions. Expected `[-](Int)` or `[!](Boolean)`.",
                    operator, basicType.toString())));
            return BasicType.ERROR_TYPE;
        }
    }
}
