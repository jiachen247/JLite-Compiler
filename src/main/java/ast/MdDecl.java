package main.java.ast;

import java.util.List;

import main.java.ast.common.Argument;
import main.java.ast.common.Id;
import main.java.ast.common.Type;
import main.java.ast.common.Util;

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
            id.toString(), Util.listToString(arguments),
            mdBody.toString());
    }
}
