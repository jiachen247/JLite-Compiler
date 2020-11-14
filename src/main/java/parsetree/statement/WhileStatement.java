package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.ir3.Label;
import main.java.ir3.LabelGenerator;
import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.BinaryExpression3;
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

public class WhileStatement extends Statement {

    private final Expression conditionPredicate;
    private final LinkedList<Statement> stmts;

    public WhileStatement(int x, int y, Expression conditionPredicate, LinkedList<Statement> stmtList) {
        super(x, y);
        this.conditionPredicate = conditionPredicate;
        this.stmts = stmtList;
    }

    @Override
    public String toString() {
        return String.format("While (%s) {\n%s}\n",
            conditionPredicate.toString(),
            Helper.getInstance().indent(Helper.getInstance().join(stmts)));
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType predicate = conditionPredicate.typeCheck(env, errors);
        if (!predicate.equals(BasicType.BOOL_TYPE)) {
            errors.add(TypeChecker.buildTypeError(conditionPredicate.x, conditionPredicate.y,
                String.format("Expected a predicate but found `%s`.", predicate)));
            return BasicType.ERROR_TYPE;
        }

        return Helper.getInstance().evalBlock(env, stmts, errors);
    }

    /*

        while (PREDICATE) { S }
        ---

        goto test;
        label start;
        S

        label test:
        PREDICATE
        if predicate.true goto start


     */
    @Override
    public Stmt3Result toIR() {
        List<Stmt3> stmt3s = new ArrayList<>();
        List<VarDecl3> tempVars = new ArrayList<>();

        Label start = LabelGenerator.getLabel();
        Label test = LabelGenerator.getLabel();


        stmt3s.add(new GotoStatement3(test));
        stmt3s.add(new LabelStatement(start));

        for (Statement statement : stmts) {
            Stmt3Result res = statement.toIR();
            tempVars.addAll(res.getTempVars());
            stmt3s.addAll(res.getStmt3List());
        }

        stmt3s.add(new LabelStatement(test));

        Exp3Result predicateResult = conditionPredicate.toIR();
        tempVars.addAll(predicateResult.getTempVars());
        stmt3s.addAll(predicateResult.getStatements());

        if (predicateResult.getResult() instanceof BinaryExpression3 || predicateResult.getResult() instanceof Idc3) {
            stmt3s.add(new IfStatement3(predicateResult.getResult(), start));
        } else {
            Id3 temp = TempVariableGenerator.getId();
            tempVars.add(new VarDecl3(BasicType.BOOL_TYPE, temp));
            stmt3s.add(new AssignmentStatement3(temp, predicateResult.getResult()));
            stmt3s.add(new IfStatement3(temp, start));
        }

        return new Stmt3Result(tempVars, stmt3s);
    }
}
