package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import main.java.staticcheckers.type.BasicType;

public class CallExpression3 implements Exp3 {
    private Id3 methodId; // globally unique
    private List<Exp3> args;
    private List<Id3> uses;

    @Override
    public BasicType getType() {
        return type;
    }

    @Override
    public List<Id3> getUses() {
        return uses;
    }

    private BasicType type;

    public CallExpression3(Id3 methodId, List<Exp3> exp3args, BasicType type) {
        this.methodId = methodId;
        this.args = exp3args;
        this.type = type;

        this.uses = new ArrayList<>();
        for (Exp3 arg: exp3args) {
            this.uses.addAll(arg.getUses());
        }
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
        int ind = args.size();

        List<Exp3> reversedArgs = new ArrayList<>(args);
        Collections.reverse(reversedArgs);

        for (Exp3 arg : reversedArgs) {
            sb.append(arg.generateArm());
            if (ind != 1) {
                sb.append(String.format("    mov a%d, a1\n", ind--));
            }
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
            sb.append("    push {a1}\n");
        }

        sb.append(String.format("    bl %s(PLT)\n", methodId));
        sb.append(String.format("    add sp, sp, #%d\n", size));
        return sb.toString();
    }
}
