package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.stmt.CallStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.expression.CallExpression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class CallStatement extends Statement {

    private CallExpression callExpression;

    public CallStatement(int x, int y, CallExpression callExpression) {
        super(x, y);
        this.callExpression = callExpression;
    }

    @Override
    public String toString() {
        return callExpression.toString() + ";";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        System.out.println("CALL STATMENT TYPECHECK");
        return callExpression.typeCheck(env, errors);
    }

    @Override
    public Stmt3Result toIR() {
        List<VarDecl3> temps = new ArrayList<>();
        List<Stmt3> stmts = new ArrayList<>();
        Exp3Result callResult = callExpression.toIR();
        temps.addAll(callResult.getTempVars());
        stmts.addAll(callResult.getStatements());

        stmts.add(new CallStatement3(callResult.getResult()));
        return new Stmt3Result(temps, stmts);
    }
}
