package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import main.java.parsetree.MdSignature;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
import main.java.staticcheckers.type.Environment;

public class CallExpression extends Expression {

    private final Expression callee;
    private final LinkedList<Expression> arguments;


    public CallExpression(int x, int y, Expression callee, LinkedList<Expression> arguments) {
        super(x, y);
        this.callee = callee;
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "[" + callee.toString() + "(" + Helper.getInstance().concat(arguments) + ")]";
    }

    @Override
    public BasicType typeCheck(Environment env, List<CheckError> errors) {
        BasicType cd;
        Id id;

        if (callee instanceof IdExpression) {
            cd = env.getClassContext().getCname();
            id = ((IdExpression) callee).id;
        } else if (callee instanceof InExpression) {
            InExpression exp = ((InExpression) callee);
            cd = exp.object.typeCheck(env, errors);
            id = exp.property;
        } else {
            System.out.println("Invalid call expression!");
            return BasicType.ERROR_TYPE;
        }

        if (cd.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (!env.getClassDescriptors().containsKey(cd)) {
            // cannot find class
            System.out.println("Unable to find class + " + cd.getName());
            return BasicType.ERROR_TYPE;
        }

        ClassDescriptor descriptor = env.getClassDescriptors().get(cd);

        List<BasicType> actual = new ArrayList<>();

        Stream<BasicType> argTypesStream = arguments.stream().map(arg -> arg.typeCheck(env, errors));

        if (argTypesStream.anyMatch(argType -> argType.equals(BasicType.ERROR_TYPE))) {
            System.out.println("Failed to parse function arguments" + arguments.toString());
            return BasicType.ERROR_TYPE;
        }

        // List<BasicType> argTypes  = argTypesStream.collect(Collectors.toList());

        MdSignature sig = new MdSignature(id.x, id.y, id, actual);
        if (!descriptor.getMethods().containsKey(sig)) {
            System.out.println("Unable to find method '" + id + "' in '" + descriptor.getCname().getName());
            return BasicType.ERROR_TYPE;
        }

        return descriptor.getMethods().get(sig).getReturnType();
    }
}
