package main.java.parsetree.statement;

import java.util.LinkedList;
import java.util.List;

import main.java.parsetree.expression.Expression;
import main.java.parsetree.shared.Helper;
import main.java.staticcheckers.CheckError;
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
            System.out.println("If statement expect a boolean function, found " + predicate);
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
            System.out.println("If and else block should return the same type " + this.toString());
            return BasicType.ERROR_TYPE;
        }
    }


}
