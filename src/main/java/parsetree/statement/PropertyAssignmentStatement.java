package main.java.parsetree.statement;

import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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

        if (objType.equals(BasicType.ERROR_TYPE) || exprType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        if (!env.getClassDescriptors().get(objType).getFields().containsKey(property)) {
            errors.add(TypeChecker.buildTypeError(property.x, property.y,
                String.format("Object `%s` does not have field `%s`.", objType.getName(), property)));
            return BasicType.ERROR_TYPE;
        }

        BasicType propType = env.getClassDescriptors().get(objType).getFields().get(property);

        if (!propType.isPrimitiveType() && exprType.equals(BasicType.NULL_TYPE)) {
            return BasicType.VOID_TYPE;
        } else if (!propType.equals(exprType)) {
            // todo handle null
            errors.add(TypeChecker.buildTypeError(expression.x, expression.y,
                String.format("Failed to assign `%s` to property of type `%s`.", exprType, propType)));
            return BasicType.ERROR_TYPE;
        } else {
            return BasicType.VOID_TYPE;
        }


    }
}
