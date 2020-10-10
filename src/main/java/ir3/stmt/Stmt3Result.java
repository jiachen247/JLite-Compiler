package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.VarDecl3;
import main.java.parsetree.shared.VarDecl;

public class Stmt3Result {
    private List<VarDecl3> tempVars;
    private List<Stmt3> stmt3List;

    public Stmt3Result(List<VarDecl3> tempVars, List<Stmt3> stmt3List) {
        this.tempVars = tempVars;
        this.stmt3List = stmt3List;
    }

    public Stmt3Result() {
        this.tempVars = new ArrayList<>();
        this.stmt3List = new ArrayList<>();
    }

    public List<VarDecl3> getTempVars() {
        return tempVars;
    }

    public List<Stmt3> getStmt3List() {
        return stmt3List;
    }
}
