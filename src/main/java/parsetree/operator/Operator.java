package main.java.parsetree.operator;

import java.util.Objects;

public abstract class Operator {

    public String getOperation() {
        return operation;
    }

    private String operation;

    public Operator(String operation) {
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
