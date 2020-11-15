package main.java.ir3.stmt;

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
        return String.format("%s%s", exp.generateArm(), GlobalOffsetTable.getInstance().getStoreInstruction(id.getName()));
    }
}
