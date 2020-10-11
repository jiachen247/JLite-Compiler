package main.java.parsetree.statement;


import java.util.List;

import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.Node;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public abstract class Statement extends Node {
    public Statement(int x, int y) {
        super(x, y);
    }

    // return BasicType("error") on error
    public abstract BasicType typeCheck(Environment env, List<CheckError> errors);

    public abstract Stmt3Result toIR();
}
