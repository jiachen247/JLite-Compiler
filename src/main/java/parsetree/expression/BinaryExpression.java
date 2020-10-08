package main.java.parsetree.expression;

import java.util.List;

import main.java.parsetree.operator.BinaryOperator;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class BinaryExpression extends Expression {

    private final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    public BinaryExpression(int x, int y, BinaryOperator operator, Expression left, Expression right) {
        super(x, y);
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](" + operator.toString() + ")";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType leftType = left.typeCheck(env, errors);
        BasicType rightType = left.typeCheck(env, errors);

        if (leftType.equals(BasicType.ERROR_TYPE) || rightType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        // overload plus op for string concat
        if (operator.getOperation().equals("+") && leftType.equals(BasicType.STRING_TYPE) && rightType.equals(BasicType.STRING_TYPE)) {
            return BasicType.STRING_TYPE;
        } else if (isBooleanOp(operator) && leftType.equals(BasicType.BOOL_TYPE) && rightType.equals(BasicType.BOOL_TYPE)) {
            return BasicType.BOOL_TYPE;
        } else if (isArithOp(operator) && leftType.equals(BasicType.INT_TYPE) && rightType.equals(BasicType.INT_TYPE)) {
            return BasicType.INT_TYPE;
        } else if (isRelOp(operator) && leftType.equals(BasicType.INT_TYPE) && rightType.equals(BasicType.INT_TYPE)) {
            return BasicType.INT_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(operator.x, operator.y,
                String.format("Type `[%s](%s, %s)` not supported for Binary Expressions. Expected `[+-*/](Int, Int)` or [<,<=,>,>=,==,!=](Bool, Bool) or `[+](String, String)` operations.",
                    operator, leftType.toString(), rightType.toString())));
            return BasicType.ERROR_TYPE;
        }
    }

    private boolean isRelOp(BinaryOperator operator) {
        return operator.getOperation().equals("<")
            || operator.getOperation().equals(">")
            || operator.getOperation().equals("<=")
            || operator.getOperation().equals(">=")
            || operator.getOperation().equals("==")
            || operator.getOperation().equals("!=");
    }

    private boolean isBooleanOp(BinaryOperator operator) {
        return operator.getOperation().equals("||")
            || operator.getOperation().equals("&&");
    }

    private boolean isArithOp(BinaryOperator operator) {
        return operator.getOperation().equals("+")
            || operator.getOperation().equals("-")
            || operator.getOperation().equals("*")
            || operator.getOperation().equals("/");
    }
}
