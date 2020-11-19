package main.java.parsetree.shared;

import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Id3;
import main.java.parsetree.Node;
import main.java.staticcheckers.type.BasicType;

public class Argument extends Node {

    public final Type type;

    public Type getType() {
        return type;
    }

    public Id getId() {
        return id;
    }

    public final Id id;

    public Argument(int x, int y, Type type, Id id) {
        super(x, y);
        this.type = type;
        this.id = id;
    }

    public Argument(Type type, Id id) {
        super(0, 0);
        this.type = type;
        this.id = id;
    }

    public VarDecl3 toVarDecl3() {
        return new VarDecl3(new BasicType(type.getName()), new Id3(id));
    }


    @Override
    public String toString() {
        return type.toString() + " " + id;
    }
}
