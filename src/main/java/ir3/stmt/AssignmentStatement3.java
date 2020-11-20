package main.java.ir3.stmt;

import java.util.List;

import main.java.arm.GlobalOffsetTable;
import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;

public class AssignmentStatement3 implements Stmt3 {

    private Id3 id;
    private Exp3 exp;

    public AssignmentStatement3(Id3 id, Exp3 exp) {
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toString() {
        return String.format(" %s = %s;", id.toString(), exp.toString());
    }

    @Override
    public String generateArm() {

        if (!GlobalOffsetTable.getInstance().getAllocator().isOnRegister(id.getName())){
            // check if spilled
            return String.format("%s%s",
                exp.generateArm("a1"),
                GlobalOffsetTable.getInstance().getStoreInstruction(id.getName(), "a1"));
        } else {
            String target = GlobalOffsetTable.getInstance().getAllocation(id.getName());
            // check if spilled
            return exp.generateArm(target);
        }

    }

    @Override
    public List<Id3> getUses() {
        return exp.getUses();
    }

    @Override
    public Id3 getDef() {
        return id;
    }
}
