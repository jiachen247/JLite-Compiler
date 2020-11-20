package main.java.ir3.exp;

import java.util.List;
import java.util.Objects;

import main.java.arm.GlobalOffsetTable;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class Id3 implements Idc3 {
    private String name;


    @Override
    public String generateArm(String target) {
        return GlobalOffsetTable.getInstance().getLoadInstruction(name, target);
    }

    public BasicType getType() {
        return type;
    }

    @Override
    public List<Id3> getUses() {
        return List.of(this);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Id3 id3 = (Id3) o;
        return Objects.equals(name, id3.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
