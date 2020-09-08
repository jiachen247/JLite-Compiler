package main.java.ast.expression;

public class BoolLiteral extends Expression {

    public final boolean value;

    public BoolLiteral(boolean value) {

        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

}
