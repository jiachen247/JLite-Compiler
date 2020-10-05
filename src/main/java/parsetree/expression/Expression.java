package main.java.parsetree.expression;


import main.java.parsetree.Node;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public abstract class Expression implements Node {
    // returns BasicType("error") on error
    public abstract BasicType typeCheck(Environment env);
}
