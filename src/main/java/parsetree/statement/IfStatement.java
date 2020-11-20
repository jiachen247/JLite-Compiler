package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.ir3.Label;
import main.java.ir3.LabelGenerator;
import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.GotoStatement3;
import main.java.ir3.stmt.IfStatement3;
import main.java.ir3.stmt.LabelStatement;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IfStatement extends Statement {

    private final Expression condition;
    private final LinkedList<Statement> ifBlock;
    private final LinkedList<Statement> elseBlock;

    public IfStatement(int x, int y, Expression condition, LinkedList<Statement> ifBlock, LinkedList<Statement> elseBlock) {
        super(x, y);
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return String.format("If (%s) {\n%s} else {\n%s}", condition.toString(),
            Helper.getInstance().indent(
                Helper.getInstance().join(ifBlock)),
            Helper.getInstance().indent(Helper.getInstance().join(elseBlock)));
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType predicate = condition.typeCheck(env, errors);
        if (!predicate.equals(BasicType.BOOL_TYPE)) {
            errors.add(TypeChecker.buildTypeError(condition.x, condition.y,
                String.format("Expected a predicate but found `%s`.", predicate)));
            return BasicType.ERROR_TYPE;
        }

        BasicType ifBlockType = Helper.getInstance().evalBlock(env, ifBlock, errors);
        BasicType elseBlockType = Helper.getInstance().evalBlock(env, elseBlock, errors);

        if (ifBlockType.equals(BasicType.ERROR_TYPE) || elseBlockType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (ifBlockType.equals(elseBlockType)) {
            // its a match
            return ifBlockType;
        } else {
            errors.add(TypeChecker.buildTypeError(this.x, this.y,
                String.format("Expected the else block to return `%s` but found `%s`.",
                    ifBlockType, elseBlockType)));
            return BasicType.ERROR_TYPE;
        }
    }

    @Override
    public Stmt3Result toIR() {
        List<VarDecl3> tempVars = new ArrayList<>();
        List<Stmt3> stmt3List = new ArrayList<>();

        Label ifLabel = LabelGenerator.getLabel();
        Label endLabel = LabelGenerator.getLabel();


        // generate ir for conditional
        Exp3Result conditionIR = condition.toIR();
        tempVars.addAll(conditionIR.getTempVars());
        stmt3List.addAll(conditionIR.getStatements());


        // if (conditionIR.getResult() instanceof BinaryExpression3 || conditionIR.getResult() instanceof Idc3) {
        // for PA3 lets not have bops in ifgoto statements
        if (conditionIR.getResult() instanceof Idc3) {
            stmt3List.add(new IfStatement3(conditionIR.getResult(), ifLabel));
        } else {
            Id3 temp = TempVariableGenerator.getId(BasicType.BOOL_TYPE);
            tempVars.add(new VarDecl3(BasicType.BOOL_TYPE, temp));
            stmt3List.add(new AssignmentStatement3(temp, conditionIR.getResult()));
            stmt3List.add(new IfStatement3(temp, ifLabel));
        }

        for (Statement statement : elseBlock) {
            Stmt3Result res = statement.toIR();
            tempVars.addAll(res.getTempVars());
            stmt3List.addAll(res.getStmt3List());
        }

        stmt3List.add(new GotoStatement3(endLabel));

        stmt3List.add(new LabelStatement(ifLabel));

        for (Statement statement : ifBlock) {
            Stmt3Result res = statement.toIR();
            tempVars.addAll(res.getTempVars());
            stmt3List.addAll(res.getStmt3List());
        }

        stmt3List.add(new LabelStatement(endLabel));


        return new Stmt3Result(tempVars, stmt3List);
    }
}
