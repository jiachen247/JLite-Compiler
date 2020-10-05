package main.java.parsetree.expression;

import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class StringLiteral extends Expression {

    private String value;

    public StringLiteral(String value) {

        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public BasicType typeCheck(Environment env) {
        return BasicType.STRING_TYPE;
    }
}
