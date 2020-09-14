package main.java.parsetree;

import java.util.LinkedList;

import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Type;

public class MainClass implements Node {

    public Type type;
    public final LinkedList<Argument> arguments;
    public final MdBody body;

    public MainClass(Type type, LinkedList<Argument> arguments, MdBody body) {
        this.type = type;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("class %s {\n\tVoid main(%s) {\n%s\t}\n}\n",
            type,
            Helper.getInstance().concat(arguments),
            Helper.getInstance().indent(body.toString()));
    }
}