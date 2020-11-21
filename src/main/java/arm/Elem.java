package main.java.arm;

import java.util.ArrayList;

class Elem {
    public String getId() {
        return id;
    }

    public ArrayList<String> getNeigh() {
        return neigh;
    }

    String id;
    ArrayList<String> neigh;

    public Elem(String id, ArrayList<String> neigh) {
        this.id = id;
        this.neigh = neigh;
    }

    @Override
    public String toString() {
        return String.format("[id='%s', neigh=%s]", id, String.join(", ", neigh));
    }
}
