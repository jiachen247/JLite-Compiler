package main.java.parsetree.statement;

import java.sql.SQLOutput;
import java.util.List;

import main.java.parsetree.shared.Id;
import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class PropertyAssignmentStatement extends Statement {

    private final Expression object;
    private final Id property;
    private final Expression expression;

    public PropertyAssignmentStatement(int x, int y, Expression object, Id property, Expression expression) {
        super(x, y);
        this.object = object;
        this.property = property;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("%s.%s = %s", object, property, expression);
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType objType = object.typeCheck(env, errors);
        BasicType exprType = expression.typeCheck(env, errors);

        if (objType.equals(BasicType.ERROR_TYPE) || exprType.equals(BasicType.ERROR_TYPE) ) {
            return BasicType.ERROR_TYPE;
        }

        if (!env.getClassDescriptors().get(objType).getFields().containsKey(property)) {
            System.out.println("Failed to find class property '" + property + "' " + this.toString());
            return BasicType.ERROR_TYPE;
        }

        BasicType propType = env.getClassDescriptors().get(objType).getFields().get(property);

        if (!propType.equals(exprType)) {
            // todo handle null
            System.out.println("Failed to assign " + exprType + " to " + propType);
            return BasicType.ERROR_TYPE;
        }

        return BasicType.VOID_TYPE;
    }
}
