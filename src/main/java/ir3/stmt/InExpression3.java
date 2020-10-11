package main.java.ir3.stmt;

import main.java.ir3.exp.Exp3;
import main.java.ir3.exp.Id3;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class InExpression3 implements Exp3 {
    private Exp3 obj;
    private Id3 prop;
    private BasicType type;

    public Exp3 getObj() {
        return obj;
    }

    public Id3 getProp() {
        return prop;
    }

    public BasicType getType() {
        return type;
    }

    public InExpression3(Exp3 obj, Id property, BasicType type) {
        this.obj = obj;
        this.prop = new Id3(property);
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(" %s.%s", obj.toString(), prop.toString());
    }
}