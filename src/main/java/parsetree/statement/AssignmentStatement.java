package main.java.parsetree.statement;


import java.util.ArrayList;
import java.util.List;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.InExpression3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.PropertyAssignmentStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.expression.Expression;
import main.java.parsetree.expression.IdExpression;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class AssignmentStatement extends Statement {

    private final Id id;
    private final Expression expression;
    private boolean isLocal;
    private BasicType classType;

    public AssignmentStatement(int x, int y, Id id, Expression expression) {
        super(x, y);
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("%s = %s;", id, expression);
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType idType = env.lookup(id);
        isLocal = env.isLocal(id);
        classType = env.getClassContext().getCname();

        if (idType.equals(BasicType.ERROR_TYPE)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Variable `%s` does not exist under the current environment.", id)));
            return BasicType.ERROR_TYPE;
        }

        BasicType expType = expression.typeCheck(env, errors);

        if (idType.equals(BasicType.ERROR_TYPE) || expType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (!idType.isPrimitiveType() && expType.equals(BasicType.NULL_TYPE)) {
            // allow assignment of null to objects
            return BasicType.VOID_TYPE;
        } else if (!idType.equals(expType)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Failed to assign `%s` to variable of type `%s`.", expType, idType)));
            return BasicType.ERROR_TYPE;
        } else {
            return BasicType.VOID_TYPE;
        }
    }

    @Override
    public Stmt3Result toIR() {
        List<Stmt3> stmt3s = new ArrayList<>();
        List<VarDecl3> tempVars = new ArrayList<>();
        Exp3Result result = expression.toIR();
        stmt3s.addAll(result.getStatements());
        tempVars.addAll(result.getTempVars());

        if (!isLocal) {


            if (expression instanceof IdExpression){
                stmt3s.add(new PropertyAssignmentStatement3(new Id3("this", classType),
                    new Id3(id.name, expression.getType()), result.getResult()));
            } else {

                Id3 temp = TempVariableGenerator.getId(expression.getType());
                tempVars.add(new VarDecl3(expression.getType(), temp));
                stmt3s.add(new AssignmentStatement3(temp, result.getResult()));

                stmt3s.add(new PropertyAssignmentStatement3(new Id3("this", classType),
                    new Id3(id.name, expression.getType()), temp));

            }
        } else {
            stmt3s.add(new AssignmentStatement3(new Id3(id.name, expression.getType()), result.getResult()));
        }

        return new Stmt3Result(tempVars, stmt3s);


    }
}
