package main.java.parsetree;

import java.util.ArrayList;
import java.util.LinkedList;

import main.java.ir3.CData3;
import main.java.ir3.CMtd3;
import main.java.ir3.CName3;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;
import main.java.staticcheckers.type.BasicType;

public class MainClass extends Node {

    public Type type;
    public final LinkedList<Argument> arguments;
    public final MdBody body;
    public String methodId;

    public MainClass(int x, int y, Type type, LinkedList<Argument> arguments, MdBody body) {
        super(x, y);
        this.type = type;
        this.arguments = arguments;
        this.body = body;
        this.methodId = String.format("%s_0", type);
    }

    @Override
    public String toString() {
        return String.format("class %s {\n\tVoid .%s_0(%s) {\n%s\t}\n}\n",
            type,
            type,
            Helper.getInstance().concat(arguments),
            Helper.getInstance().indent(body.toString()));
    }

    public CData3 toCData3() {
        return new CData3(new CName3(type.getName()), new ArrayList<>());
    }

    public CMtd3 toCMtd3() {
        return new CMtd3(BasicType.VOID_TYPE, new Id(methodId), arguments, body.toMdBody3(), true);
    }
}
