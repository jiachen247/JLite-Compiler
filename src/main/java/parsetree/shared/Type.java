package main.java.parsetree.shared;

import java.util.Objects;

import main.java.parsetree.Node;


public class Type extends Node {

    public String getName() {
        return name;
    }

    private String name;

    public Type(int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(name, type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
