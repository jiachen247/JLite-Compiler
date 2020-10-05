package main.java.parsetree;

import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;

public class MdDecl implements Node {

    public final MdSignature signature;
    public final List<Argument> arguments;

    public MdSignature getSignature() {
        return signature;
    }

    public List<Argument> getArguments() {
        return arguments;
    }

    public MdBody getMdBody() {
        return mdBody;
    }

    public final MdBody mdBody;

    public MdDecl(Type type, Id id, List<Argument> args, MdBody mdBody) {
        this.signature = new MdSignature(type, id, args.stream().map(arg -> arg.type).collect(Collectors.toList()));
        this.arguments = args;
        this.mdBody = mdBody;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}",
            signature.returnType.toString(),
            signature.id.toString(), Helper.getInstance().concat(arguments),
            mdBody.toString());
    }

}
