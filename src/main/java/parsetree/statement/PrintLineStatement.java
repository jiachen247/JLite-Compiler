package main.java.parsetree.statement;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.Result;
import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.PrintLineStatement3;
import main.java.ir3.stmt.Stmt3;
import main.java.ir3.stmt.Stmt3Result;
import main.java.parsetree.expression.Expression;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class PrintLineStatement extends Statement {
    private Expression expr;
    private BasicType type;

    public PrintLineStatement(int x, int y, Expression expr) {
        super(x, y);
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "println(" + expr.toString() + ");";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        type = expr.typeCheck(env, errors);

        if (type.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (type.equals(BasicType.INT_TYPE) || type.equals(BasicType.BOOL_TYPE) || type.equals(BasicType.STRING_TYPE)) {
            // arg should be a int, bool, string
            return BasicType.VOID_TYPE;
        } else {
            errors.add(TypeChecker.buildTypeError(expr.x, expr.y,
                String.format("The `println` function takes either a `Int`, `String` or `Boolean` as input but found type `%s` instead.",
                    type)));
            return BasicType.ERROR_TYPE;
        }
    }

    @Override
    public Stmt3Result toIR() {
        List<Stmt3> stmt3s = new ArrayList<>();
        List<VarDecl3> tempVars = new ArrayList<>();

        Exp3Result res = expr.toIR();

        if (res.getResult() instanceof Idc3) {
            stmt3s.add(new PrintLineStatement3(res.getResult()));
            return new Stmt3Result(tempVars, stmt3s);
        } else {
            Id3 temp1 = TempVariableGenerator.getId();
            tempVars.add(new VarDecl3(type, temp1));
            stmt3s.add(new AssignmentStatement3(temp1, res.getResult()));
            stmt3s.add(new PrintLineStatement3(temp1));
            return new Stmt3Result(tempVars, stmt3s);
        }
    }
}
