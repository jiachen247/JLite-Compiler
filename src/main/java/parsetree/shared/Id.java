package main.java.parsetree.shared;

import main.java.parsetree.Node;

public class Id extends Node {

    public String name;

    public Id(int x, int y, String name) {
        super(x, y);
        this.name = name;
    }

    public Id(String name) {
        super(0, 0);
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
        } else if (o instanceof Id) {
            return ((Id) o).name.equals(name);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
