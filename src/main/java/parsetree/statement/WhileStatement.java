package main.java.parsetree.statement;

import java.util.LinkedList;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;

public class WhileStatement extends Statement {

    private final Expression conditionPredicate;
    private final LinkedList<Statement> stmts;

    public WhileStatement(Expression conditionPredicate, LinkedList<Statement> stmtList) {
        this.conditionPredicate = conditionPredicate;
        this.stmts = stmtList;
    }

    @Override
    public String toString() {
        return String.format("While (%s) {\n%s}\n",
            conditionPredicate.toString(),
            Helper.getInstance().indent(Helper.getInstance().join(stmts)));
    }
}
