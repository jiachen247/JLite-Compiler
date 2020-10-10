package main.java.parsetree.expression;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ir3.Result;
import main.java.ir3.exp.Exp3Result;
import main.java.parsetree.MdSignature;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
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
            errors.add(TypeChecker.buildTypeError(callee.x, callee.y, "Invalid call expression."));
            return BasicType.ERROR_TYPE;
        }

        if (cd.equals(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        } else if (!env.getClassDescriptors().containsKey(cd)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Object `%s` does not have field `%s`.", cd.getName(), id)));
            return BasicType.ERROR_TYPE;
        }

        ClassDescriptor descriptor = env.getClassDescriptors().get(cd);

        List<BasicType> argTypes = arguments.stream().map(arg -> arg.typeCheck(env, errors)).collect(Collectors.toList());

        if (argTypes.contains(BasicType.ERROR_TYPE)) {
            return BasicType.ERROR_TYPE;
        }

        MdSignature sig = new MdSignature(id.x, id.y, id, argTypes);
        if (argTypes.contains(BasicType.NULL_TYPE)) {
            // check for any matching method signatures
            List<MdSignature> possible = descriptor.getMethods().keySet()
                .stream()
                .filter((mdSig) -> match(argTypes, mdSig.argTypes))
                .collect(Collectors.toList());


            if (possible.size() == 0) {
                errors.add(TypeChecker.buildTypeError(id.x, id.y,
                    String.format("Class `%s` does not contain a method with this signature `%s`.", descriptor.getCname(), sig.toString())));
                return BasicType.ERROR_TYPE;
            } else if (possible.size() == 1) {
                sig = possible.get(0); // replace sig if unique possible method sig found
            } else {
                String possibleStr = possible.stream().map(MdSignature::toString).collect(Collectors.joining(","));
                errors.add(TypeChecker.buildTypeError(id.x, id.y,
                    String.format("Reference to method `%s` is ambiguous. Could be possibly referring to `%s`", sig, possibleStr)));
                return BasicType.ERROR_TYPE;
            }
        } else if (!descriptor.getMethods().containsKey(sig)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Class `%s` does not contain a method with this signature `%s`.", descriptor.getCname(), sig.toString())));
            return BasicType.ERROR_TYPE;
        }

        return descriptor.getMethods().get(sig).getReturnType();
    }

    // actual type could contains nulls
    // (null, null) should match to (String, Dummy)
    private boolean match(List<BasicType> actualTypes, List<BasicType> types) {
        if (actualTypes.size() != types.size()) {
            return false;
        }
        int n = actualTypes.size();

        for (int i = 0; i < n; i++) {
            BasicType x = actualTypes.get(i);
            BasicType y = types.get(i);

            if (!x.equals(y) && (!x.equals(BasicType.NULL_TYPE) || y.isPrimitiveType())) {
                // can pass in null for objects
                return false;
            }
        }
        return true;
    }

    @Override
    public Exp3Result toIR() {
        return new Exp3Result();
    }
}
