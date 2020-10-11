package main.java.parsetree.expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ir3.TempVariableGenerator;
import main.java.ir3.VarDecl3;
import main.java.ir3.exp.CallExpression3;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Exp3Result;
import main.java.ir3.exp.Id3;
import main.java.ir3.exp.Idc3;
import main.java.ir3.stmt.AssignmentStatement3;
import main.java.ir3.stmt.InExpression3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.MdSignature;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.TypeChecker;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
import main.java.staticcheckers.type.Environment;
import main.java.staticcheckers.type.FunctionType;

public class CallExpression extends Expression {

    private final Expression callee;
    private final LinkedList<Expression> arguments;

    // for IR
    private String methodId;

    private BasicType cd;
    private Id id;
    private BasicType returnType;

    private IdExpression idExpression;
    private InExpression inExpression;


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
        System.out.println("DO I TYPE CHECK THIS CALL EXP??!??!?");
        if (callee instanceof IdExpression) {
            cd = env.getClassContext().getCname();
            idExpression = ((IdExpression) callee);
            id = idExpression.id;
        } else if (callee instanceof InExpression) {
            inExpression = ((InExpression) callee);
            System.out.println("ARGHHH " + inExpression.object);
            cd = inExpression.object.typeCheck(env, errors);
            id = inExpression.property;
            System.out.println("cd " + cd + " id " + id);
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
                String possibleStr = possible.stream().map(MdSignature::toString).collect(Collectors.joining(", "));
                errors.add(TypeChecker.buildTypeError(id.x, id.y,
                    String.format("Reference to method `%s` is ambiguous. Could be possibly referring to `%s`", sig, possibleStr)));
                return BasicType.ERROR_TYPE;
            }
        } else if (!descriptor.getMethods().containsKey(sig)) {
            errors.add(TypeChecker.buildTypeError(id.x, id.y,
                String.format("Class `%s` does not contain a method with this signature `%s`.", descriptor.getCname(), sig.toString())));
            return BasicType.ERROR_TYPE;
        }

        FunctionType ft = descriptor.getMethods().get(sig);
        methodId = ft.methodId;
        returnType = ft.getReturnType();
        return returnType;
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
        List<VarDecl3> temps = new ArrayList<>();
        List<Stmt3> stmt3s = new ArrayList<>();

        System.out.println("[Callepression] " + this.toString());
        System.out.println(String.format("cd: %s, returnType: %s, id: %s", cd, returnType, id));
        System.out.println(inExpression);

        Exp3Result calleeResult;
        Exp3 thisContext;

        if (idExpression != null) {
            calleeResult = idExpression.toIR();
            thisContext = new Id3(cd.getName());
        } else {
            calleeResult = inExpression.object.toIR();
            thisContext = calleeResult.getResult();
            if (!(thisContext instanceof Idc3)) {
                Id3 temp1 = TempVariableGenerator.getId();
                System.out.println("BLAHH " + temp1 + " " + calleeResult);
                temps.add(new VarDecl3(cd, temp1));
                stmt3s.add(new AssignmentStatement3(temp1, thisContext));
                thisContext = temp1;
            }
        }

        temps.addAll(calleeResult.getTempVars());
        stmt3s.addAll(calleeResult.getStatements());

        Exp3 calleeObj = calleeResult.getResult();
        System.out.println("callobj " + callee);
//        if (!(calleeObj instanceof Idc3)) {
//            Id3 temp1 = TempVariableGenerator.getId();
//            System.out.println("BLAHH " + temp1 + " " + calleeObj );
//            temps.add(new VarDecl3(calleeType, temp1));
//            stmt3s.add(new AssignmentStatement3(temp1, calleeObj));
//            calleeObj = temp1;
//        }

        if (calleeObj instanceof InExpression3) {
            calleeObj = ((InExpression3) calleeObj).getObj();
        }

        List<Exp3> exp3args = new ArrayList<>();

        // put this as the first obj
        exp3args.add(thisContext);

        for (Expression arg : arguments) {
            Exp3Result argResult = arg.toIR();
            temps.addAll(argResult.getTempVars());
            stmt3s.addAll(argResult.getStatements());

            Exp3 exp = argResult.getResult();

            if (!(exp instanceof Idc3)) {
                Id3 temp = TempVariableGenerator.getId();
                System.out.println("BLAHH " + temp + " " + exp);
                temps.add(new VarDecl3(arg.getType(), temp));
                stmt3s.add(new AssignmentStatement3(temp, exp));
                exp = temp;
            }

            exp3args.add(exp);
        }


        System.out.println(methodId);
        System.out.println(methodId + "++++");

        /// eval expressions for args
        // add this to be first arg

        return new Exp3Result(temps, stmt3s, new CallExpression3(new Id3(methodId), exp3args));
    }

    @Override
    public BasicType getType() {
        return returnType;
    }
}
