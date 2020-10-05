package main.java.parsetree.statement;


    import main.java.parsetree.Node;
    import main.java.staticcheckers.type.BasicType;
    import main.java.staticcheckers.type.Environment;

public abstract class Statement implements Node {
    // returns BasicType("error") on error
    public abstract BasicType typeCheck(Environment env);
}
