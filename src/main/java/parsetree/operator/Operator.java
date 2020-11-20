package main.java.parsetree.operator;

import java.util.Objects;

import main.java.parsetree.Node;

public abstract class Operator extends Node {

    public String getOperation() {
        return operation;
    }

    private String operation;

    public Operator(int x, int y, String operation) {
        super(x, y);
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operator operator = (Operator) o;
        return Objects.equals(operation, operator.operation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operation);
    }
}
