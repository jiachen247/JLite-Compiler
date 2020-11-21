package main.java.ir3.exp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import main.java.arm.Allocation;
import main.java.arm.GlobalOffsetTable;
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
        for (Exp3 arg : exp3args) {
            this.uses.addAll(arg.getUses());
        }
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", methodId,
            args.stream().map(Object::toString).collect(Collectors.joining(", ")));
    }


    public String generateArm(String target) {
        return args.size() <= 4 ? callWithArgumentsInRegisters(target) : callWithArgumentsOnStack(target);
    }

    private String callWithArgumentsInRegisters(String target) {
        StringBuilder sb = new StringBuilder();
        int ind = args.size();

        List<Exp3> reversedArgs = new ArrayList<>(args);
        Collections.reverse(reversedArgs);
        sb.append(Allocation.pusha3a4);

        for (Exp3 arg : reversedArgs) {
            sb.append(arg.generateArm("a" + ind--));
//            if (ind != 1) {
//                sb.append(String.format("    mov a%d, a1\n", ind--));
//            }
        }

        sb.append(String.format("    bl %s(PLT)\n", methodId));
        sb.append(Allocation.popa3a4);
        if (target != null && !target.equals("a1")) {
            sb.append(String.format("    mov %s, a1\n", target));
        }

        return sb.toString();
    }

    private String callWithArgumentsOnStack(String target) {
        StringBuilder sb = new StringBuilder();
        List<Exp3> copy = new ArrayList<>(args);
        Collections.reverse(copy);

        int size = args.size() * 4;
//        sb.append(String.format("    sub sp, sp, %d\n", size));

        sb.append(Allocation.pusha3a4);

        // push in reversed order
        for (Exp3 arg : copy) {
            if (arg instanceof Id3 && GlobalOffsetTable.getInstance().getAllocator().isOnRegister(((Id3) arg).getName())) {
                sb.append(String.format("    push {%s}\n",
                    GlobalOffsetTable.getInstance().getAllocation(((Id3) arg).getName())));
            } else {
                sb.append(arg.generateArm("a1"));
                sb.append("    push {a1}\n");
            }
        }

        sb.append(String.format("    bl %s(PLT)\n", methodId));
        sb.append(String.format("    add sp, sp, #%d\n", size));
        sb.append(Allocation.popa3a4);
        if (target != null && !target.equals("a1")) {
            sb.append(String.format("    mov %s, a1\n", target));
        }
        return sb.toString();
    }
}
