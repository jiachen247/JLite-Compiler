package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.BinaryExpression3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.operator.BinaryOperator;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class BinaryExpression extends Expression {

    private final BinaryOperator operator;
    public final Expression left;
    public final Expression right;
    private BasicType type;

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
        BasicType rightType = right.typeCheck(env, errors);

        if (leftType.equals(BasicType.ERROR_TYPE) || rightType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        // overload plus op for string concat
        if (operator.getOperation().equals("+") && leftType.equals(BasicType.STRING_TYPE) && rightType.equals(BasicType.STRING_TYPE)) {
            type = BasicType.STRING_TYPE;
            return BasicType.STRING_TYPE;
        } else if (isBooleanOp(operator) && leftType.equals(BasicType.BOOL_TYPE) && rightType.equals(BasicType.BOOL_TYPE)) {
            type = BasicType.BOOL_TYPE;
            return BasicType.BOOL_TYPE;
        } else if (isArithOp(operator) && leftType.equals(BasicType.INT_TYPE) && rightType.equals(BasicType.INT_TYPE)) {
            type = BasicType.INT_TYPE;
            return BasicType.INT_TYPE;
        } else if (isRelOp(operator) && leftType.equals(BasicType.INT_TYPE) && rightType.equals(BasicType.INT_TYPE)) {
            type = BasicType.BOOL_TYPE;
            return BasicType.BOOL_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(operator.x, operator.y,
                String.format("Type `[%s](%s, %s)` not supported for Binary Expressions. Expected `[+-*/](Int, Int)` or [<,<=,>,>=,==,!=](Bool, Bool) or `[+](String, String)` operations.",
                    operator, leftType.toString(), rightType.toString())));
            return BasicType.ERROR_TYPE;
        }
    }

    @Override
    public Exp3Result toIR() {
        List<VarDecl3> temps = new ArrayList<>();
        List<Stmt3> stmt3s = new ArrayList<>();
        Exp3 leftOperand, rightOperand;


        Exp3Result leftResult = left.toIR();
        Exp3Result rightResult = right.toIR();

        stmt3s.addAll(leftResult.getStatements());
        stmt3s.addAll(rightResult.getStatements());
        temps.addAll(leftResult.getTempVars());
        temps.addAll(rightResult.getTempVars());

        if (!(leftResult.getResult() instanceof Idc3)) {
            Id3 temp1 = TempVariableGenerator.getId(type);
            temps.add(new VarDecl3(type, temp1));
            stmt3s.add(new AssignmentStatement3(temp1, leftResult.getResult()));
            leftOperand = temp1;
        } else {
            leftOperand = leftResult.getResult();
        }

        if (!(rightResult.getResult() instanceof Idc3)) {
            Id3 temp2 = TempVariableGenerator.getId(type);
            temps.add(new VarDecl3(type, temp2));
            stmt3s.add(new AssignmentStatement3(temp2, rightResult.getResult()));
            rightOperand = temp2;
        } else {
            rightOperand = rightResult.getResult();
        }

//        Id3 temp = TempVariableGenerator.getId();
//        temps.add(new VarDecl3(type, temp));
//        stmt3s.add(new AssignmentStatement3(temp,
//            new BinaryExpression3(operator, leftResult.getResult(), rightResult.getResult(), type)));


        return new Exp3Result(temps, stmt3s,
            new BinaryExpression3(operator, rightOperand, leftOperand, type));
    }

    @Override
    public BasicType getType() {
        return type;
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
