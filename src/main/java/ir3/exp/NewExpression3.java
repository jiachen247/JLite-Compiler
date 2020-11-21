package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.arm.Allocation;
import main.java.arm.ClassOffsetTable;
import main.java.staticcheckers.type.BasicType;

public class NewExpression3 implements Exp3 {
    private BasicType type;

    @Override
    public String toString() {
        return String.format("new %s()", type.getName());
    }

    public NewExpression3(BasicType basicType) {
        type = basicType;
    }

    @Override
    public String generateArm(String target) {
        // Should calloc instead of malloc to get a zero initialized buffer
        return String.format(
            "%s    mov a2, #%d\n" +
                "    mov a1, #1\n" +
                "    bl calloc(PLT)\n" +
                "    mov %s, a1\n%s",
            Allocation.pusha3a4,
            ClassOffsetTable.getInstance().getClassSize(type.getName()),
            target,
            Allocation.popa3a4
        );
    }

    @Override
    public BasicType getType() {
        return type;
    }

    @Override
    public List<Id3> getUses() {
        return new ArrayList<>();
    }
}
