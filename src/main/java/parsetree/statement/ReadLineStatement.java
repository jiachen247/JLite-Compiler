package main.java.parsetree.statement;

import java.util.List;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class ReadLineStatement extends Statement {
    private Id id;

    public ReadLineStatement(int x, int y, Id id) {
        super(x, y);
        this.id = id;
    }

    @Override
    public String toString() {
        return "readln(" + id.toString() + ");";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType input = env.lookup(id);
        if (input.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        } else if (input.equals(BasicType.INT_TYPE) || input.equals(BasicType.BOOL_TYPE) || input.equals(BasicType.STRING_TYPE)) {
            // arg should be a int, bool, string
            return BasicType.VOID_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("The `readln` function takes either an id of `Int`, `String` or `Boolean` as input but found variable of type `%s` instead.",
                    input)));
            return BasicType.ERROR_TYPE;
        }
    }
}
