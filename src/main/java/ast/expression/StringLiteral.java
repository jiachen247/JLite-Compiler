package main.java.ast.expression;

public class StringLiteral extends Expression {

    private String value;

    public StringLiteral(String value) {

        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
