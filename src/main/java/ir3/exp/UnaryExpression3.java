package main.java.ir3.exp;

import main.java.parsetree.operator.BinaryOperator;
import main.java.parsetree.operator.UnaryOperator;
import main.java.staticcheckers.type.BasicType;

public class UnaryExpression3 implements Exp3 {
    private UnaryOperator operator;
    private Exp3 exp;
    private BasicType type;

    @Override
    public String toString() {
        return String.format("%s(%s)", operator.getOperation(), exp.toString());
    }

    public UnaryExpression3(UnaryOperator operator, Exp3 exp, BasicType type) {
        this.operator = operator;
        this.exp = exp;
    }
}
