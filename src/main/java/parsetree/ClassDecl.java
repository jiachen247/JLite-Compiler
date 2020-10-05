package main.java.parsetree;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Type;
import main.java.parsetree.shared.VarDecl;

public class ClassDecl implements Node {

    public Type type;
    public final List<VarDecl> varDeclList;

    public Type getType() {
        return type;
    }

    public List<VarDecl> getVarDeclList() {
        return varDeclList;
    }

    public List<MdDecl> getMdDeclList() {
        return mdDeclList;
    }

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
            Helper.getInstance().indent(Helper.getInstance().join(varDeclList)),
            Helper.getInstance().indent(Helper.getInstance().join(mdDeclList)));
    }
}
