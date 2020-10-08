package main.java.parsetree.statement;

import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class PrintLineStatement extends Statement {
    private Expression expr;

    public PrintLineStatement(int x, int y, Expression expr) {
        super(x, y);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "println(" + expr.toString() + ");";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType type = expr.typeCheck(env, errors);
        if (type.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (type.equals(BasicType.INT_TYPE) || type.equals(BasicType.BOOL_TYPE) || type.equals(BasicType.STRING_TYPE)) {
            // arg should be a int, bool, string
            return BasicType.VOID_TYPE;
        } else {
            System.out.println("println() takes in either a int, bool or string, found " + type);
            return BasicType.ERROR_TYPE;
        }
    }
}
