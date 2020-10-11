package main.java.parsetree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.ir3.CMtd3;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;
import main.java.staticcheckers.type.BasicType;

public class MdDecl extends Node {

    public final MdSignature signature;
    public final List<Argument> arguments;
    public final MdBody mdBody;
    private String methodId;

    public BasicType getReturnType() {
        return returnType;
    }

    public final BasicType returnType;

    public MdSignature getSignature() {
        return signature;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public MdBody getMdBody() {
        return mdBody;
    }

    public MdDecl(int x, int y, Type type, Id id, List<Argument> args, MdBody mdBody) {
        super(x, y);
        this.signature = new MdSignature(type.x, type.y, id, args.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList()));
        this.arguments = args;
        this.mdBody = mdBody;
        this.returnType = BasicType.fromType(type);
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}",
            returnType.toString(),
            signature.id.toString(),
            Helper.getInstance().concat(arguments),
            mdBody.toString());
    }

    public CMtd3 toCMd3(ClassDecl classDecl) {
        Id methodId = new Id(getUniqueMethodId());

        List<Argument> newArgs = new ArrayList<>();

        // add class as first argument
        newArgs.add(new Argument(classDecl.type, new Id("this")));
        newArgs.addAll(arguments);

        return new CMtd3(returnType, methodId, newArgs, mdBody.toMdBody3());
    }

    public void setUniqueClassMethodIndex(ClassDecl context, long index) {
        this.methodId = String.format("%%%s_%d", context.type.getName(), index);
    }

    public String getUniqueMethodId() {
        return methodId;
    }
}
