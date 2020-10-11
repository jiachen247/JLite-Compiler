package main.java.parsetree.expression;


import java.util.ArrayList;
import java.util.List;

import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.NewExpression3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.Type;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class NewExpression extends Expression {

    private Type type;

    public NewExpression(int x, int y, Type type) {
        super(x, y);
        this.type = type;
    }

    @Override
    public String toString() {
        return "new " + type.toString() + "()";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType bt = BasicType.fromType(type);

        if (!env.getClassDescriptors().containsKey(bt)) {
            errors.add(TypeChecker.buildTypeError(type.x, type.y,
                String.format("Class `%s` does not exist.", type)));
            return BasicType.ERROR_TYPE;
        }
        return bt;
    }

    @Override
    public Exp3Result toIR() {
        List<VarDecl3> tempVars = new ArrayList<>();
        List<Stmt3> stmt3List = new ArrayList<>();
        return new Exp3Result(tempVars, stmt3List, new NewExpression3(new BasicType(type.getName())));
    }

    @Override
    public BasicType getType() {
        return new BasicType(type.getName());
    }
}
