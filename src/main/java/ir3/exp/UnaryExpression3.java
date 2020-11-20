package main.java.ir3.exp;

import java.util.List;

import main.java.parsetree.operator.UnaryOperator;
import main.java.staticcheckers.type.BasicType;

public class UnaryExpression3 implements Exp3 {
    private UnaryOperator operator;
    private Exp3 exp;
    private BasicType type;
    private List<Id3> uses;

    @Override
    public String toString() {
        return String.format("%s(%s)", operator.getOperation(), exp.toString());
    }

    public UnaryExpression3(UnaryOperator operator, Exp3 exp, BasicType type) {
        this.operator = operator;
        this.exp = exp;
        this.type = type;
        this.uses = exp.getUses();

    }

    @Override
    public String generateArm(String target) {
        String exprArm = exp.generateArm(target);
        String opArm = operator.generateArm(target);
        return String.format("%s%s", exprArm, opArm);
    }

    @Override
    public BasicType getType() {
        return BasicType.INT_TYPE;
    }

    @Override
    public List<Id3> getUses() {
        return uses;
    }
}
