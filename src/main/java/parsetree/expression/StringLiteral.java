package main.java.parsetree.expression;

import java.util.List;

import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class StringLiteral extends Expression {

    private String value;

    public StringLiteral(int x, int y, String value) {
        super(x, y);

        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        return BasicType.STRING_TYPE;
    }
}
