package main.java.arm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Allocation {
    public final static String REG_V1 = "v1";
    public final static String REG_V2 = "v2";
    public final static String REG_V3 = "v3";
    public final static String REG_V4 = "v4";
    public final static String REG_V5 = "v5";
    public final static String REG_A3 = "a3";
    public final static String REG_A4 = "a4";
    public final static String SPILL = "spill";
    public final static Integer NUM_REGISTER = 7;
    public final static List<String> registers = List.of(REG_V1, REG_V2, REG_V3, REG_V4, REG_V5, REG_A3, REG_A4);
    public static String pusha3a4 = "";
    public static String popa3a4 = "";

    private HashMap<String, String> registerMap;

    public Allocation(ArrayList<String> spilled) {
        registerMap = new HashMap<>();

        for (String s : spilled) {
            spill(s);
        }
    }

    public void spill(String id) {
        registerMap.put(id, SPILL);
    }

    public void assign(String id, String register) {
        registerMap.put(id, register);
    }

    public boolean contains(String id) {
        return registerMap.containsKey(id);
    }

    public boolean isSpilled(String id) {
        return registerMap.get(id).equals(SPILL);
    }

    public String lookup(String id) {
        return contains(id) ? registerMap.get(id) : null;
    }

    @Override
    public String toString() {
        return String.format("=== Allocation ===\n" +
            "%s\n" +
            "==== End Allocation ===\n", registerMap);
    }

    public boolean isClassVariable(String name) {
        return !contains(name);
    }

    public List<String> getSpilled() {
        ArrayList<String> spilled = new ArrayList<>();
        for (String k : registerMap.keySet()) {
            if (isSpilled(k)) {
                spilled.add(k);
            }
        }
        return spilled;
    }

    public boolean containsA3() {
        return registerMap.containsValue(REG_A3);
    }

    public boolean containsA4() {
        return registerMap.containsValue(REG_A4);
    }

    public void initPushPopA3A4() {
        boolean a3 = containsA3();
        boolean a4 = containsA4();

        if (a3 && a4) {
            Allocation.pusha3a4 = "    push {a3, a4}\n";
            Allocation.popa3a4 = "    pop {a3, a4}\n";
        } else if (a3) {
            Allocation.pusha3a4 = "    push {a3}\n";
            Allocation.popa3a4 = "    pop {a3}\n";
        } else if (a4) {
            Allocation.pusha3a4 = "    push {a4}\n";
            Allocation.popa3a4 = "    pop {a4}\n";
        }
    }

    public boolean isOnRegister(String id) {
        return !isClassVariable(id) && !isSpilled(id);
    }
}
