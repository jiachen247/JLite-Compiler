package main.java.parsetree;

import java.util.List;

import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;

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
            id.toString(), Helper.getInstance().concat(arguments),
            mdBody.toString());
    }
}
