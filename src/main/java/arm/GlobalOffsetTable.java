package main.java.arm;

import java.util.HashMap;

import main.java.ir3.Program3;

public class GlobalOffsetTable {
    private static GlobalOffsetTable _this = null;
    public static HashMap<String, Integer> methodOffsetTable = null;

    private RegisterAllocator registerAllocator;

    private GlobalOffsetTable() {

    }

    public static GlobalOffsetTable getInstance() {
        if (_this == null) {
            _this = new GlobalOffsetTable();
        }
        return _this;
    }

    public String getStoreInstruction(String varname) {
        if (!methodOffsetTable.containsKey(varname)) {
            // todo cache at the class level


            // Since its not in the method scope, it has to be in the class scope.
            return String.format("    ldr a4, [fp, #%d]\n%s",
                methodOffsetTable.getOrDefault("this", 9999),
                ClassOffsetTable.getInstance().getStoreInstruction(Program3.getCurrentClass(), varname));
        }

        if (registerAllocator.contains(varname)) {
            // str to register instead of mem
            return String.format("    mov %s, a1\n",
                registerAllocator.getReg(varname));
        } else {
            return String.format("    str a1, [fp, #%d]\n",
                methodOffsetTable.getOrDefault(varname, 9999));
        }
    }

    public String getLoadInstruction(String varname) {
        if (!methodOffsetTable.containsKey(varname)) {
            // Since its not in the method scope, it has to be in the class scope.
            return String.format("    ldr a4, [fp, #%d]\n%s",
                methodOffsetTable.getOrDefault("this", 9999),
                ClassOffsetTable.getInstance().getLoadInstruction(Program3.getCurrentClass(), varname));
        }

        StringBuilder sb = new StringBuilder();

        if (!registerAllocator.contains(varname)) {
            sb.append(registerAllocator.getReg(varname));
        }

        sb.append(String.format("    mov a1, %s\n",
            registerAllocator.getReg(varname)));

        return sb.toString();
    }

    public void setMethodOffsetTable(HashMap<String, Integer> methodOffsetTable) {
        GlobalOffsetTable.methodOffsetTable = methodOffsetTable;
    }


    public void setAllocation(Allocation allocation) {
        this.registerAllocator = new RegisterAllocator(allocation);
    }
}
