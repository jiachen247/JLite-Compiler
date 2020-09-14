package main.java.parsetree.operator;

public abstract class Operator {

    private String operation;

    public Operator(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return operation;
    }
}
