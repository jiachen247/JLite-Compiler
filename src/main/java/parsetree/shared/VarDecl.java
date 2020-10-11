package main.java.parsetree.shared;

import main.java.parsetree.Node;

public class VarDecl extends Node {

    public final Type type;
    public final Id id;

    public VarDecl(int x, int y, Type type, Id id) {
        super(x, y);
        this.type = type;
        this.id = id;
    }

    public VarDecl(Type type, Id id) {
        super(0, 0);
        this.type = type;
        this.id = id;
    }


    @Override
    public String toString() {
        return type.toString() + " " + id + ";";
    }
}
