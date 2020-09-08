package main.java.ast;

import java.util.LinkedList;

import main.java.ast.common.Argument;
import main.java.ast.common.Type;
import main.java.ast.common.Util;

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
        return String.format("class %s {\n\tVoid main(%s) {\n%s\t}\n}", type, Util.listToString(arguments), Util.indent(body.toString()));
    }
}
