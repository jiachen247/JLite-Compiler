package main.java.ir3;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.VarDecl;

public class Result {
    private List<VarDecl> tempVars;
    private List<Stmt3> stmt3List;

    public Result(List<VarDecl> tempVars, List<Stmt3> stmt3List) {
        this.tempVars = tempVars;
        this.stmt3List = stmt3List;
    }

    public Result() {
        this.tempVars = new ArrayList<>();
        this.stmt3List = new ArrayList<>();
    }

    public List<VarDecl> getTempVars() {
        return tempVars;
    }

    public List<Stmt3> getStmt3List() {
        return stmt3List;
    }
}
