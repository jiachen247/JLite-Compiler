package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Result;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.StringLiteral3;
import main.java.ir3.stmt.Stmt3;
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

    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new StringLiteral3(value));
    }
}
