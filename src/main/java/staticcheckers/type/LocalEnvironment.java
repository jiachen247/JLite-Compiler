package main.java.staticcheckers.type;

import java.util.HashMap;

import main.java.parsetree.shared.Id;

public class LocalEnvironment {
    public HashMap<Id, BasicType> local;
    public LocalEnvironment(HashMap<Id, BasicType> local) {
        this.local = local;
    }
}
