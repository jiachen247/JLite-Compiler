package main.java.parsetree.shared;

import main.java.parsetree.Node;

public class Argument extends Node {

    public final Type type;
    public final Id id;

    public Argument(int x, int y, Type type, Id id) {
        super(x, y);
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id;
    }
}
