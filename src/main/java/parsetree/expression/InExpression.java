package main.java.parsetree.expression;


import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.exp.InExpression3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class InExpression extends Expression {

    public final Expression object;
    public final Id property;
    public BasicType objType;
    public BasicType returnType;

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
        objType = object.typeCheck(env, errors);
        if (objType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        if (!env.getClassDescriptors().get(objType).getFields().containsKey(property)) {
            errors.add(TypeChecker.buildTypeError(property.x, property.y,
                String.format("Object `%s` does not have field `%s`.", objType.getName(), property)));
            return BasicType.ERROR_TYPE;
        }

        returnType = env.getClassDescriptors().get(objType).getFields().get(property);
        return returnType;

    }

    @Override
    public Exp3Result toIR() {
        List<VarDecl3> tempVars = new ArrayList<>();
        List<Stmt3> stmt3List = new ArrayList<>();

        Exp3Result objectResult = object.toIR();
        stmt3List.addAll(objectResult.getStatements());
        tempVars.addAll(objectResult.getTempVars());
        Exp3 obj = objectResult.getResult();

        if (!(objectResult.getResult() instanceof Idc3)) {
            Id3 temp = TempVariableGenerator.getId(objType);
            tempVars.add(new VarDecl3(objType, temp));
            stmt3List.add(new AssignmentStatement3(temp, objectResult.getResult()));
            obj = temp;
        }

        //
        Id3 ret = TempVariableGenerator.getId(returnType);
        tempVars.add(new VarDecl3(returnType, ret));
        stmt3List.add(new AssignmentStatement3(ret, new InExpression3(obj, property, returnType)));

        return new Exp3Result(tempVars, stmt3List, ret);
    }

    @Override
    public BasicType getType() {
        return returnType;
    }


}