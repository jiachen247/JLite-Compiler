package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.InExpression3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IdExpression extends Expression {
    public Id id;
    private BasicType type;
    private boolean isLocal;
    private BasicType classType;

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
        type = env.lookup(id);
        isLocal = env.isLocal(id);
        classType = env.getClassContext().getCname();

        if (type.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        }
        return type;
    }

    @Override
    public Exp3Result toIR() {
        if (isLocal) {
            return new Exp3Result(new ArrayList<>(), new ArrayList<>(), new Id3(id.name, type));
        } else {
            // in class context
            return new Exp3Result(
                new ArrayList<>(),
                new ArrayList<>(),
                new InExpression3(new Id3("this", classType), id, type));
        }
    }

    @Override
    public BasicType getType() {
        return type;
    }

}
