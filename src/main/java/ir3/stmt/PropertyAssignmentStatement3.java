package main.java.ir3.stmt;

import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;

public class PropertyAssignmentStatement3 implements Stmt3 {
    private Exp3 obj;
    private Id3 prop;
    private Exp3 expr;

    @Override
    public String toString() {
        return String.format(" %s.%s = %s;", obj.toString(), prop.toString(), expr.toString());
    }

    public PropertyAssignmentStatement3(Exp3 obj, Id3 prop, Exp3 expr) {
        this.obj = obj;
        this.prop = prop;
        this.expr = expr;
    }
}
