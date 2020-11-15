package main.java.ir3.exp;

import main.java.arm.GlobalOffsetTable;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class Id3 implements Idc3 {
    private String name;

    public BasicType getType() {
        return type;
    }

    private BasicType type;

    public Id3(Id id) {
        this.name = id.name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + "";
    }

    public Id3(String name, BasicType type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String generateArm() {
        return GlobalOffsetTable.getInstance().getLoadInstruction(name);
    }
}
