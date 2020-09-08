package main.java.ast;

import java.util.List;

import main.java.ast.common.Type;
import main.java.ast.common.Util;
import main.java.ast.common.VarDecl;

public class ClassDecl implements Node {

    public Type type;
    public final List<VarDecl> varDeclList;
    public final List<MdDecl> mdDeclList;

    public ClassDecl(Type type, List<VarDecl> varDeclList, List<MdDecl> mdDeclList) {
        this.type = type;
        this.varDeclList = varDeclList;
        this.mdDeclList = mdDeclList;
    }

    @Override
    public String toString() {
        return String.format("class %s {\n%s\n%s}\n",
            type.toString(),
            Util.indent(Util.joinWithNewLine(varDeclList)),
            Util.indent(Util.joinWithNewLine(mdDeclList)));
    }
}
