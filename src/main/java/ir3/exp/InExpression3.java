package main.java.ir3.exp;

import java.util.List;

import main.java.arm.ClassOffsetTable;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class InExpression3 implements Exp3 {
    private Exp3 obj;
    private Id3 prop;
    private BasicType type;

    public BasicType getType() {
        return type;
    }

    @Override
    public List<Id3> getUses() {
        return obj.getUses();
    }

    public InExpression3(Exp3 obj, Id property, BasicType type) {
        this.obj = obj;
        this.prop = new Id3(property.name, type);
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(" %s.%s", obj.toString(), prop.toString());
    }

    @Override
    public String generateArm(String target) {
        return String.format("%s%s",
            obj.generateArm(target),
            ClassOffsetTable.getInstance().getLoadInstruction(obj.getType().getName(), prop.getName(), target));
    }
}
