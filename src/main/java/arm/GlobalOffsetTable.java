package main.java.arm;

import java.util.HashMap;

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

    public String getStoreInstruction(String name) {
        if (!methodOffsetTable.containsKey(name)) {
            System.out.println("Error: Failed to look up variable in the GOT.");
            return "";
        }

        return String.format("    str v1, [fp, #%d]\n", methodOffsetTable.getOrDefault(name, 9999));
    }

    public String getLoadInstruction(String name) {
        if (!methodOffsetTable.containsKey(name)) {
            System.out.println("Error: Failed to look up variable in the GOT.");
            return "";
        }

        return String.format("    ldr v1, [fp, #%d]\n", methodOffsetTable.getOrDefault(name, 9999));
    }


    public HashMap<String, Integer> getMethodOffsetTable() {
        return methodOffsetTable;
    }

    public void setMethodOffsetTable(HashMap<String, Integer> methodOffsetTable) {
        GlobalOffsetTable.methodOffsetTable = methodOffsetTable;
    }
}
