package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.PropertyAssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class PropertyAssignmentStatement extends Statement {

    private Expression object;
    private Id property;
    private Expression expression;
    private BasicType type;

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

        type = env.getClassDescriptors().get(objType).getFields().get(property);

        if (!type.isPrimitiveType() && exprType.equals(BasicType.NULL_TYPE)) {
            return BasicType.VOID_TYPE;
        } else if (!type.equals(exprType)) {
            // todo handle null
            errors.add(TypeChecker.buildTypeError(expression.x, expression.y,
                String.format("Failed to assign `%s` to property of type `%s`.", exprType, type)));
            return BasicType.ERROR_TYPE;
        } else {
            return BasicType.VOID_TYPE;
        }
    }

    @Override
    public Stmt3Result toIR() {
        List<VarDecl3> tempVars = new ArrayList<>();
        List<Stmt3> stmt3List = new ArrayList<>();

        Exp3Result objectResult = object.toIR();
        stmt3List.addAll(objectResult.getStatements());
        tempVars.addAll(objectResult.getTempVars());
        Exp3 obj = objectResult.getResult();

        if (!(objectResult.getResult() instanceof Idc3)) {
            Id3 temp = TempVariableGenerator.getId();
            tempVars.add(new VarDecl3(type, temp));
            stmt3List.add(new AssignmentStatement3(temp, objectResult.getResult()));
            obj = temp;
        }

        Exp3Result expResult = expression.toIR();
        stmt3List.addAll(expResult.getStatements());
        tempVars.addAll(expResult.getTempVars());

        stmt3List.add(new PropertyAssignmentStatement3(obj, new Id3(property.name), expResult.getResult()));
        return new Stmt3Result(tempVars, stmt3List);
    }
}
