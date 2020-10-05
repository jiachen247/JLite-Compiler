package main.java.parsetree.expression;

import main.java.parsetree.operator.BinaryOperator;
import main.java.parsetree.operator.PlusOperator;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class BinaryExpression extends Expression {

    private final BinaryOperator operator;
    public final Expression left;
    public final Expression right;

    public BinaryExpression(BinaryOperator operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "[" + left.toString() + ", " + right.toString() + "](" + operator.toString() + ")";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        BasicType leftType = left.typeCheck(env);
        BasicType rightType = left.typeCheck(env);

        if (leftType.equals(BasicType.ERROR_TYPE) || rightType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        // overload plus op for string concat
        if (operator.equals(new PlusOperator()) && leftType.equals(BasicType.STRING_TYPE) && rightType.equals(BasicType.STRING_TYPE)) {
            return BasicType.STRING_TYPE;
        } else if (leftType.equals(BasicType.INT_TYPE) && rightType.equals(BasicType.INT_TYPE)) {
            if (isArithOp(operator)) {
                return BasicType.INT_TYPE;
            } else {
                // Relational op
                return BasicType.BOOL_TYPE;
            }
        } else {
            System.out.println("unsupported bop!");
            return BasicType.ERROR_TYPE;
        }
    }

    private boolean isArithOp(BinaryOperator operator) {
        return operator.getOperation().equals("+")
            || operator.getOperation().equals("-")
            || operator.getOperation().equals("*")
            || operator.getOperation().equals("/");
    }
}
