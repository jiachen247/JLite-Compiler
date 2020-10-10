package main.java.parsetree;

import java.util.Collections;
import java.util.List;

import main.java.ir3.CData3;
import main.java.ir3.CName3;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Type;
import main.java.parsetree.shared.VarDecl;

public class ClassDecl extends Node {

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

    public ClassDecl(int x, int y, Type type, List<VarDecl> varDeclList, List<MdDecl> mdDeclList) {
        super(x, y);
        this.type = type;
        this.varDeclList = varDeclList;
        this.mdDeclList = mdDeclList;
        Collections.reverse(mdDeclList);
    }

    @Override
    public String toString() {
        return String.format("class %s {\n%s\n%s}\n",
            type.toString(),
            Helper.getInstance().indent(Helper.getInstance().join(varDeclList)),
            Helper.getInstance().indent(Helper.getInstance().join(mdDeclList)));
    }

    public CData3 toCData3() {
        return new CData3(new CName3(type.getName()), varDeclList);
    }
}
