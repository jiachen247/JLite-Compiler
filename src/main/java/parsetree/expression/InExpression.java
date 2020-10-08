package main.java.parsetree.expression;


import java.util.List;
import java.util.Map;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class InExpression extends Expression {

    public final Expression object;
    public final Id property;

    public InExpression(int x, int y, Expression object, Id property) {
        super(x, y);
        this.object = object;
        this.property = property;
    }

    @Override
    public String toString() {
        return object.toString() + "." + property.toString();
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType objType = object.typeCheck(env, errors);
        if (objType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        if (!env.getClassDescriptors().get(objType).getFields().containsKey(property)) {
            System.out.println("Failed to find method '" + property + "'" + this.toString());
            return BasicType.ERROR_TYPE;
        }
        return env.getClassDescriptors().get(objType).getFields().get(property);
    }
}