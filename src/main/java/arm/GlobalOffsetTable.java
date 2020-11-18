package main.java.arm;

import java.util.HashMap;

import main.java.ir3.Program3;

public class GlobalOffsetTable {
    private static GlobalOffsetTable _this = null;
    private static HashMap<String, Integer> methodOffsetTable = null;

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
            // Since its not in the method scope, it has to be in the class scope.
            return String.format("    ldr a4, [fp, #%d]\n%s",
                methodOffsetTable.getOrDefault("this", 9999),
                ClassOffsetTable.getInstance().getStoreInstruction(Program3.getCurrentClass(), varname));
        }

        return String.format("    str a1, [fp, #%d]\n", methodOffsetTable.getOrDefault(varname, 9999));
    }

    public String getLoadInstruction(String varname) {
        if (!methodOffsetTable.containsKey(varname)) {
            // Since its not in the method scope, it has to be in the class scope.
            return String.format("    ldr a4, [fp, #%d]\n%s",
                methodOffsetTable.getOrDefault("this", 9999),
                ClassOffsetTable.getInstance().getLoadInstruction(Program3.getCurrentClass(), varname));
        }

        return String.format("    ldr a1, [fp, #%d]\n", methodOffsetTable.getOrDefault(varname, 9999));
    }

    public void setMethodOffsetTable(HashMap<String, Integer> methodOffsetTable) {
        GlobalOffsetTable.methodOffsetTable = methodOffsetTable;
    }
}
