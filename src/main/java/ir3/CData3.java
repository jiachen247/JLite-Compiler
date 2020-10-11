package main.java.ir3;

import java.util.List;

import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.VarDecl;

public class CData3 {
    private CName3 cname;
    private List<VarDecl> variables;

    public CData3(CName3 cname, List<VarDecl> variables) {
        this.cname = cname;
        this.variables = variables;
    }

    @Override
    public String toString() {
        return String.format("class %s {\n%s}\n",
            cname.getName(),
            Helper.getInstance().indent(Helper.getInstance().join(variables)));
    }
}
