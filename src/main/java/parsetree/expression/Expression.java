package main.java.parsetree.expression;


import java.util.List;

import main.java.parsetree.Node;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public abstract class Expression extends Node {
    public Expression(int x, int y) {
        super(x, y);
    }

    // return BasicType("error") on error
    public abstract BasicType typeCheck(Environment env, List<CheckError> errors);
}
