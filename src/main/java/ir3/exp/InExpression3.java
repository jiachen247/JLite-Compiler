package main.java.ir3.exp;

import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class InExpression3 implements Exp3 {
    private Exp3 obj;
    private Id3 prop;
    private BasicType type;

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

    @Override
    public String generateArm() {
        return "in expression";
    }
}
