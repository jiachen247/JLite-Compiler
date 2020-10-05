package main.java.parsetree.statement;


import main.java.parsetree.Node;
import main.java.staticcheckers.type.Environment;

public abstract class Statement implements Node {
    public abstract boolean typeCheck(Environment env);
}
