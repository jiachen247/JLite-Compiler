package main.java.parsetree.statement;

import java.util.LinkedList;
import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class WhileStatement extends Statement {

    private final Expression conditionPredicate;
    private final LinkedList<Statement> stmts;

    public WhileStatement(int x, int y, Expression conditionPredicate, LinkedList<Statement> stmtList) {
        super(x, y);
        this.conditionPredicate = conditionPredicate;
        this.stmts = stmtList;
    }

    @Override
    public String toString() {
        return String.format("While (%s) {\n%s}\n",
            conditionPredicate.toString(),
            Helper.getInstance().indent(Helper.getInstance().join(stmts)));
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType predicate = conditionPredicate.typeCheck(env, errors);
        if (!predicate.equals(BasicType.BOOL_TYPE)) {
            System.out.println("While statement expect a boolean function, found " + predicate);
            return BasicType.ERROR_TYPE;
        }

        return Helper.getInstance().evalBlock(env, stmts, errors);
    }
}
