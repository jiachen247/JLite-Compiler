package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.ir3.VarDecl3;
import main.java.ir3.stmt.Stmt3;
import main.java.parsetree.shared.VarDecl;

public class Exp3Result {
    private List<VarDecl3> tempVars;
    private List<Stmt3> statements;
    private Exp3 result;


    public Exp3Result(List<VarDecl3> tempVars, List<Stmt3> statements, Exp3 result) {
        this.tempVars = tempVars;
        this.statements = statements;
        this.result = result;
    }

    public List<Stmt3> getStatements() {
        return statements;
    }

    public Exp3 getResult() {
        return result;
    }

    public List<VarDecl3> getTempVars() {
        return tempVars;
    }


    public Exp3Result() {
        this.tempVars = new ArrayList<>();
        this.statements = new ArrayList<>();
        this.result = new BoolLiteral3(true);
    }
}
