package main.java.staticcheckers.type;

import main.java.parsetree.shared.Type;

public class BasicType {
    private String name;



    public  BasicType(String name) {
        this.name = name;
    }

    public static BasicType fromType(Type type) {
        if (type.getName().equals(INT_TYPE.name)) {
            return INT_TYPE;
        } else if (type.getName().equals(VOID_TYPE.name)) {
            return VOID_TYPE;
        } else if (type.getName().equals(STRING_TYPE.name)) {
            return STRING_TYPE;
        } else if (type.getName().equals(CHAR_TYPE.name)) {
            return CHAR_TYPE;
        } else if (type.getName().equals(BOOL_TYPE.name)) {
            return BOOL_TYPE;
        } else {
            return new BasicType(type.getName());
        }
    }

    @Override
    public String toString() {
        return "BasicType{" +
            "name='" + name + '\'' +
            '}';
    }

    public static BasicType INT_TYPE = new BasicType("Int");
    public static BasicType VOID_TYPE = new BasicType("Void");
    public static BasicType STRING_TYPE = new BasicType("String");
    public static BasicType CHAR_TYPE = new BasicType("Char");
    public static BasicType BOOL_TYPE = new BasicType("Bool");
}
