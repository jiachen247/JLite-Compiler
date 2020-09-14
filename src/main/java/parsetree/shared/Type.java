package main.java.parsetree.shared;

import main.java.parsetree.Node;

public class Type implements Node {

    public String name;

    public Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
