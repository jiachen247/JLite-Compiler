package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IdExpression extends Expression {

    public Id id;
    private BasicType type;

    public IdExpression(int x, int y, Id id) {
        super(x, y);
        this.id = id;
        this.type = new BasicType(id.name);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType basicType = env.lookup(id);
        if (basicType.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        }

        return basicType;
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new Id3(id.name));
    }

    @Override
    public BasicType getType() {
        return type;
    }

}
