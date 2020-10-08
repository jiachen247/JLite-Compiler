package main.java.staticcheckers.type;

import java.util.Objects;

import main.java.parsetree.shared.Type;

public class BasicType {
    public String getName() {
        return name;
    }

    private String name;


    public BasicType(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicType basicType = (BasicType) o;
        return Objects.equals(name, basicType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public static BasicType fromType(Type type) {
        if (type.getName().equals(INT_TYPE.name)) {
            return INT_TYPE;
        } else if (type.getName().equals(VOID_TYPE.name)) {
            return VOID_TYPE;
        } else if (type.getName().equals(STRING_TYPE.name)) {
            return STRING_TYPE;
        } else if (type.getName().equals(BOOL_TYPE.name)) {
            return BOOL_TYPE;
        } else {
            return new BasicType(type.getName());
        }
    }

    @Override
    public String toString() {
        return name;
    }

    // is non object --> can be set to null
    public boolean isPrimitiveType() {
        return this.equals(INT_TYPE)
            || this.equals(VOID_TYPE)
            || this.equals(BOOL_TYPE);
    }


    public static BasicType INT_TYPE = new BasicType("Int");
    public static BasicType VOID_TYPE = new BasicType("Void");
    public static BasicType STRING_TYPE = new BasicType("String");
    public static BasicType BOOL_TYPE = new BasicType("Bool");

    // for internal use only
    public static BasicType NULL_TYPE = new BasicType("null");
    public static BasicType ERROR_TYPE = new BasicType("error");
}