package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.stmt.EmptyReturnStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class EmptyReturnStatement extends Statement {
    public EmptyReturnStatement(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        return "return ;";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return BasicType.VOID_TYPE;
    }

    @Override
    public Stmt3Result toIR() {
        List<Stmt3> stmts = new ArrayList<>();
        stmts.add(new EmptyReturnStatement3());
        return new Stmt3Result(new ArrayList<>(), stmts);
    }
}
