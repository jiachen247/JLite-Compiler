package main.java.parsetree.statement;

import java.util.LinkedList;
import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class IfStatement extends Statement {

    private final Expression condition;
    private final LinkedList<Statement> ifBlock;
    private final LinkedList<Statement> elseBlock;

    public IfStatement(int x, int y, Expression condition, LinkedList<Statement> ifBlock, LinkedList<Statement> elseBlock) {
        super(x, y);
        this.condition = condition;
        this.ifBlock = ifBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return String.format("If (%s) {\n%s} else {\n%s}", condition.toString(),
            Helper.getInstance().indent(
                Helper.getInstance().join(ifBlock)),
            Helper.getInstance().indent(Helper.getInstance().join(elseBlock)));
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType predicate = condition.typeCheck(env, errors);
        if (!predicate.equals(BasicType.BOOL_TYPE)) {
            errors.add(TypeChecker.buildTypeError(condition.x, condition.y,
                String.format("Expected a predicate but found `%s`.", predicate)));
            return BasicType.ERROR_TYPE;
        }

        BasicType ifBlockType = Helper.getInstance().evalBlock(env, ifBlock, errors);
        BasicType elseBlockType = Helper.getInstance().evalBlock(env, elseBlock, errors);

        if (ifBlockType.equals(BasicType.ERROR_TYPE) || elseBlockType.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (ifBlockType.equals(elseBlockType)) {
            // its a match
            return ifBlockType;
        } else {
            errors.add(TypeChecker.buildTypeError(this.x, this.y,
                String.format("Expected the else block to return `%s` but found `%s`.",
                    ifBlockType, elseBlockType)));
            return BasicType.ERROR_TYPE;
        }
    }


}
