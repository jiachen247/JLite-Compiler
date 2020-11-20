package main.java.ir3.stmt;

import java.util.ArrayList;
import java.util.List;

import main.java.arm.ClassOffsetTable;
import main.java.arm.GlobalOffsetTable;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;

public class PropertyAssignmentStatement3 implements Stmt3 {
    private Exp3 obj;
    private Id3 prop;
    private Exp3 expr;
    private List<Id3> uses;

    @Override
    public String toString() {
        return String.format(" %s.%s = %s;", obj.toString(), prop.toString(), expr.toString());
    }

    public PropertyAssignmentStatement3(Exp3 obj, Id3 prop, Exp3 expr) {
        this.obj = obj;
        this.prop = prop;
        this.expr = expr;
        this.uses = new ArrayList<>();

        uses.addAll(obj.getUses());
        uses.addAll(expr.getUses());
    }

    @Override
    public String generateArm() {
        String target = "a4";

        return String.format("%s%s%s",
            GlobalOffsetTable.getInstance().getLoadInstruction(((Id3) obj).getName(), target),
            expr.generateArm("a1"),
            ClassOffsetTable.getInstance().getStoreInstruction(obj.getType().getName(), prop.getName()));
    }

    @Override
    public List<Id3> getUses() {
        return uses;
    }

    @Override
    public Id3 getDef() {
        // dont consider class objects
        return null;
    }
}
