package main.java.ir3.exp;

import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.shared.Argument;

public class CallExpression3 implements Exp3 {
    private Id3 methodId; // globally unique
    private List<Exp3> args;

    public CallExpression3(Id3 methodId, List<Exp3> exp3args) {
        this.methodId = methodId;
        this.args = exp3args;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", methodId,
            args.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }
}
