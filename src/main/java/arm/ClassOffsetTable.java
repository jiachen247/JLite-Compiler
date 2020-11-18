package main.java.arm;

import java.util.HashMap;
import java.util.List;

import main.java.ir3.CData3;
import main.java.parsetree.shared.VarDecl;

public class ClassOffsetTable {
    private static ClassOffsetTable _this = null;
    private HashMap<String, Integer> classOffsetTable = null;
    private HashMap<String, Integer> classSizeTable = null;

    /*
        Layout for memory in heap

        Assume all 4 bytes so no padding is needed

        -> 0x0 | EMPTY
            0x4 | first obj
            0x8 | second
            0x12 | third object
     */

    private ClassOffsetTable() {

    }

    public static ClassOffsetTable getInstance() {
        if (_this == null) {
            _this = new ClassOffsetTable();
        }
        return _this;
    }


    public String getStoreInstruction(String cname, String varname) {
        String key = formatKey(cname, varname);
        if (!classOffsetTable.containsKey(key)) {
            System.out.println(
                String.format("Error(getStoreInstruction): Failed to lookup '%s'", key));
            return "";
        }

        return String.format("    str a1, [a4, #%d]\n", classOffsetTable.getOrDefault(key, 9999));
    }

    public String getLoadInstruction(String cname, String varname) {
        String key = formatKey(cname, varname);
        if (!classOffsetTable.containsKey(key)) {
            System.out.println(
                String.format("Error(getLoadInstruction): Failed to lookup '%s'", key));
            return "";
        }

        return String.format("    ldr a1, [a4, #%d]\n", classOffsetTable.getOrDefault(key, 9999));
    }

    public void print() {
        System.out.println("=== Class Offset Table ===");

        System.out.println("Class Size Table:");
        System.out.println(classSizeTable);
        System.out.println("");

        System.out.println("Class Variable Offsets:");
        System.out.println(classOffsetTable);
        System.out.println("");

        System.out.println("=== End of Class Offset Table ===");

    }

    public Integer getClassSize(String cname) {
        return classSizeTable.getOrDefault(cname, 9999);
    }

    public void init(List<CData3> classes) {
        this.classOffsetTable = new HashMap<>();
        this.classSizeTable = new HashMap<>();

        for (CData3 clazz : classes) {
            String cname = clazz.getCname().getName();
            int size = 4; // size must be non neg for malloc (even for empty classes)

            size += clazz.getVariables().size() * 4;
            classSizeTable.put(cname, size);

            int offset = 4;
            for (VarDecl decl: clazz.getVariables()) {
                String key = formatKey(cname, decl.id.name);
                classOffsetTable.put(key, offset);
                offset += 4;
            }

        }
    }

    private String formatKey(String cname, String varname) {
        return String.format("%s.%s", cname, varname);
    }
}
