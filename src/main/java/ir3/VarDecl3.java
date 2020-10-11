package main.java.ir3;

import main.java.ir3.exp.Id3;
import main.java.parsetree.shared.VarDecl;
import main.java.staticcheckers.type.BasicType;

public class VarDecl3 {
    private final BasicType type;
    private final Id3 id;

    public VarDecl3(BasicType type, Id3 id) {
        this.type = type;
        this.id = id;
    }

    public VarDecl3(VarDecl v) {
        this.type = new BasicType(v.type.getName());
        this.id = new Id3(v.id.name);
    }

    @Override
    public String toString() {
        return " " + type + " " + id + ";";
    }
}
