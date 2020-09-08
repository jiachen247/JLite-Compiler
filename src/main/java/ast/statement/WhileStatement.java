package main.java.ast.statement;

import java.util.LinkedList;

import main.java.ast.common.Util;
import main.java.ast.expression.Expression;

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
            Util.indent(Util.joinWithNewLine(stmts)));
    }
}
