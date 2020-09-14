package main.java.parsetree.expression;

public class IntLiteral extends Expression {
    public final int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
