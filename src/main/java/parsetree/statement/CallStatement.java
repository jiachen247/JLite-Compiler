package main.java.parsetree.statement;

import java.util.List;

import main.java.ir3.Result;
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
        return callExpression.typeCheck(env, errors);
    }

    @Override
    public Stmt3Result toIR() {
        // todo impl
        return new Stmt3Result();
    }
}
