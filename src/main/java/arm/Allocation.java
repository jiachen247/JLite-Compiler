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
    public final static String SPILL = "spill";
    public final static Integer NUM_REGISTER = 5;
    public final static List<String> registers = List.of(REG_V1, REG_V2, REG_V3, REG_V4, REG_V5);
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
        return "Allocation{" +
            "registerMap=" + registerMap +
            '}';
    }
}
