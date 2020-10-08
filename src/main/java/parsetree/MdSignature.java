package main.java.parsetree;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;
import main.java.staticcheckers.type.BasicType;

public class MdSignature extends Node {

    public final Id id;
    public final List<BasicType> argTypes;

    public MdSignature(int x, int y, Id id, List<BasicType> types) {
        super(x, y);
        this.id = id;
        this.argTypes = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MdSignature that = (MdSignature) o;

        // Overloading rules -> method sig is equal iff id and arg types are equal
        // Can be overloaded with different return types if arg types are diff
        return this.id.equals(that.id) && argTypes.equals(that.argTypes);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, argTypes);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)",id.name, argTypes.stream().map(BasicType::toString).collect(Collectors.joining(",")));
    }


}
