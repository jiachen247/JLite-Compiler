package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.List;

import main.java.arm.ClassOffsetTable;
import main.java.arm.GlobalOffsetTable;
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
        return String.format(
            "    mov a1, #%d\n" +
                "    bl malloc(PLT)\n" +
                "    mov %s, a1\n",
            ClassOffsetTable.getInstance().getClassSize(type.getName()),
            target
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
