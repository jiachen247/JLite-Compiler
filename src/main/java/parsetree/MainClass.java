package main.java.ast;

import java.util.LinkedList;

import main.java.ast.common.Argument;
import main.java.ast.common.Helper;
import main.java.ast.common.Type;

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
        return String.format("class %s {\n\tVoid main(%s) {\n%s\t}\n}", type, Helper.getInstance().concat(arguments), main.java.ast.common.Helper.indent(body.toString()));
    }
}
