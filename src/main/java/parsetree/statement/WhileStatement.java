package main.java.parsetree.statement;

import java.util.LinkedList;
import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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
            errors.add(TypeChecker.buildTypeError(conditionPredicate.x, conditionPredicate.y,
                String.format("Expected a predicate but found `%s`.", predicate)));
            return BasicType.ERROR_TYPE;
        }

        return Helper.getInstance().evalBlock(env, stmts, errors);
    }
}
