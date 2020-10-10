package main.java.ir3.exp;

import main.java.parsetree.shared.Id;

public class Id3 implements Idc3 {
    private String name;

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

    public Id3(String name) {
        this.name = name;
    }
}
