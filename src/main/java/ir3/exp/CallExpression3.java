package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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


    public String generateArm() {
        return args.size() <= 4 ? callWithArgumentsInRegisters() : callWithArgumentsOnStack();
    }

    private String callWithArgumentsInRegisters() {
        StringBuilder sb = new StringBuilder();
        int ind = 1;

        for (Exp3 arg : args) {
            sb.append(arg.generateArm());
            sb.append(String.format("    mov a%d, v1\n", ind++));
        }

        sb.append(String.format("    bl %s(PLT)\n", methodId));

        return sb.toString();
    }

    private String callWithArgumentsOnStack() {
        StringBuilder sb = new StringBuilder();
        List<Exp3> copy = new ArrayList<>(args);
        Collections.reverse(copy);

        int size = args.size() * 4;
//        sb.append(String.format("    sub sp, sp, %d\n", size));

        // push in reversed order
        for (Exp3 arg : copy) {
            sb.append(arg.generateArm());
            sb.append("    str v1, [sp, #-4]!\n");
        }

        sb.append(String.format("    bl %s(PLT)\n", methodId));
        sb.append(String.format("    add sp, sp, #%d", size));
        return sb.toString();
    }


}
