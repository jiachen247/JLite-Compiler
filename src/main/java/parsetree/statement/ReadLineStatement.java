package main.java.parsetree.statement;

import java.util.List;
import java.util.jar.JarOutputStream;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
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
            return BasicType.ERROR_TYPE;
        } else if (input.equals(BasicType.INT_TYPE) || input.equals(BasicType.BOOL_TYPE) || input.equals(BasicType.STRING_TYPE)) {
            // arg should be a int, bool, string
            return BasicType.VOID_TYPE;
        } else {
            System.out.println("readlin() takes in either a int, bool or string, found " + input);
            return BasicType.ERROR_TYPE;
        }
    }
}
