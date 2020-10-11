package main.java.ir3;

import java.util.List;

import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class CMtd3 {
    private BasicType type;

    private Id id;
    private List<Argument> arguments;
    private MdBody3 body;

    public CMtd3(BasicType type, Id id, List<Argument> arguments, MdBody3 body) {
        this.type = type;
        this.id = id;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}\n",
            type.toString(),
            id.toString(),
            Helper.getInstance().concat(arguments),
            body.toString());
    }
}
