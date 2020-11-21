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

    public String getStoreInstruction(String varname, String target) {
        if (registerAllocator.getAllocation().isClassVariable(varname)) {
            // todo cache at the class level
            // Since its not in the method scope, it has to be in the class scope.
            return String.format("%s%s",
                getLoadInstruction("this", target),
                ClassOffsetTable.getInstance().getStoreInstruction(Program3.getCurrentClass(), varname));
        } else if (!registerAllocator.getAllocation().isSpilled(varname)) {
            // str to register instead of mem
//            return String.format("    mov %s, a1\n",
//                registerAllocator.getReg(varname));

            // nothing to str
            return "";
        } else {
            return String.format("    str %s, [fp, #%d]\n",
                target,
                methodOffsetTable.getOrDefault(varname, 9993));
        }
    }

    public String getLoadInstruction(String varname, String target) {
        if (registerAllocator.getAllocation().isClassVariable(varname)) {
            // Since its not in the method scope, it has to be in the class scope.

            return String.format("%s%s",
                getLoadInstruction("this", target),
                ClassOffsetTable.getInstance().getLoadInstruction(Program3.getCurrentClass(), varname, target, target));
        }

//        StringBuilder sb = new StringBuilder();
//
//        if (!registerAllocator.contains(varname)) {
//            sb.append(registerAllocator.getReg(varname));
//        }
//
//        sb.append(String.format("    mov %s, %s\n",
//            target,
//            registerAllocator.getReg(varname)));
//
//        return sb.toString();


        if (!registerAllocator.getAllocation().isSpilled(varname)) {
            return String.format("    mov %s, %s\n", target, registerAllocator.getReg(varname));
        } else {
            return String.format("    ldr %s, [fp, #%d]\n",
                target,
                methodOffsetTable.getOrDefault(varname, 9999));
        }
    }

    public void setMethodOffsetTable(HashMap<String, Integer> methodOffsetTable) {
        GlobalOffsetTable.methodOffsetTable = methodOffsetTable;
    }


    public void setAllocation(Allocation allocation) {
        this.registerAllocator = new RegisterAllocator(allocation);
    }

    public String getAllocation(String id) {
        return registerAllocator.getAllocation().lookup(id);
    }

    public Allocation getAllocator() {
        return registerAllocator.getAllocation();
    }
}
