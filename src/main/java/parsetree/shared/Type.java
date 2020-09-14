package main.java.ast.common;

import main.java.ast.Node;

public class Type implements Node {

    public String name;

    public Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (o instanceof Type) {
            return ((Type) o).name.equals(name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
