package main.java.parsetree.statement;

import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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
            errors.add(TypeChecker.buildTypeError(expr.x, expr.y,
                String.format("The `println` function takes either a `Int`, `String` or `Boolean` as input but found type `%s` instead.",
                    type)));
            return BasicType.ERROR_TYPE;
        }
    }
}
