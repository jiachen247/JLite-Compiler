package main.java.parsetree.shared;

public class Argument {

    public final Type type;
    public final Id id;

    public Argument(Type type, Id id) {

        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return type.toString() + " " + id;
    }
}
