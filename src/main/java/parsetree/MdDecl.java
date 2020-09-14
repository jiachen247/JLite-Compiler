package main.java.ast;

import java.util.List;

import main.java.ast.common.Argument;
import main.java.ast.common.Id;
import main.java.ast.common.Type;

public class MdDecl implements Node {

    public final Type type;
    public final Id id;
    public final List<Argument> arguments;
    public final MdBody mdBody;

    public MdDecl(Type type, Id id, List<Argument> args, MdBody mdBody) {

        this.type = type;
        this.id = id;
        this.arguments = args;
        this.mdBody = mdBody;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}",
            type.toString(),
            id.toString(), main.java.ast.common.Helper.concat(arguments),
            mdBody.toString());
    }
}
