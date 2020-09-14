package main.java.parsetree.shared;

import main.java.parsetree.Node;

public class VarDecl implements Node {

    public final Type type;
    public final Id id;

    public VarDecl(Type type, Id id) {

        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id + ";";
    }
}
