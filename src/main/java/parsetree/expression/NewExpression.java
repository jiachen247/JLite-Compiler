package main.java.parsetree.expression;


import java.util.List;

import main.java.parsetree.shared.Type;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
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
        BasicType basictype = BasicType.fromType(type);

        if (!env.getClassDescriptors().containsKey(basictype)) {
            errors.add(TypeChecker.buildTypeError(type.x, type.y,
                String.format("Class `%s` does not exist.", type)));
            return BasicType.ERROR_TYPE;
        }

        return basictype;
    }
}
