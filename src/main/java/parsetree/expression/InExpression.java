package main.java.parsetree.expression;


import java.util.Map;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class InExpression extends Expression {

    public final Expression object;
    public final Id property;

    public InExpression(Expression object, Id property) {
        this.object = object;
        this.property = property;
    }

    @Override
    public String toString() {
        return object.toString() + "." + property.toString();
    }

    @Override
    public BasicType typeCheck(Environment env) {
        BasicType objType = object.typeCheck(env);
        if (objType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        if (!env.getClassDescriptors().get(new Id(objType.getName())).getFields().containsKey(property)) {
            System.out.println("Failed to find method '" + property + "'" + this.toString());
            return BasicType.ERROR_TYPE;
        }
        return env.getClassDescriptors().get(new Id(objType.getName())).getFields().get(property);
    }
}