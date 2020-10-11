package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.exp.UnaryExpression3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.operator.UnaryOperator;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class UnaryExpression extends Expression {

    private UnaryOperator operator;
    private Expression expression;
    private BasicType type;

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
            type = BasicType.INT_TYPE;
            return BasicType.INT_TYPE;
        } else if (basicType.equals(BasicType.BOOL_TYPE) && operator.getOperation().equals("!")) {
            type = BasicType.BOOL_TYPE;
            return BasicType.BOOL_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(operator.x, operator.y,
                String.format("Type `[%s](%s)` not supported for Unary Expressions. Expected `[-](Int)` or `[!](Boolean)`.",
                    operator, basicType.toString())));
            return BasicType.ERROR_TYPE;
        }
    }

    @Override
    public Exp3Result toIR() {
        List<Stmt3> stmt3s = new ArrayList<>();
        List<VarDecl3> tempVars = new ArrayList<>();

        Exp3Result result = expression.toIR();
        stmt3s.addAll(result.getStatements());
        tempVars.addAll(result.getTempVars());

        if (result.getResult() instanceof Idc3) {
            return new Exp3Result(tempVars, stmt3s, new UnaryExpression3(operator, result.getResult(), type));
        } else {
            Id3 temp1 = TempVariableGenerator.getId();
            tempVars.add(new VarDecl3(type, temp1));
            stmt3s.add(new AssignmentStatement3(temp1, result.getResult()));
            return new Exp3Result(tempVars, stmt3s, new UnaryExpression3(operator, temp1, type));
        }
    }

    @Override
    public BasicType getType() {
        return type;
    }
}
