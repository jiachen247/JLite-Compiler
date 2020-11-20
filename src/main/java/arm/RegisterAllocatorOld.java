package main.java.arm;

import java.util.LinkedList;

public class RegisterAllocatorOld {
    private LinkedList<AllocationOld> queue;
    private int MAX_SIZE = 5;

    public RegisterAllocatorOld() {
        this.queue = new LinkedList<>();
    }

    public boolean contains(String varname) {
        for (AllocationOld allocationOld : queue) {
            if (allocationOld.getId().equals(varname)) {
                return true;
            }
        }
        return false;
    }

    public AllocationOld getAllocation(String varname) {

        for (AllocationOld allocationOld : queue) {
            if (allocationOld.getId().equals(varname)) {
                // push allocation to the back of the queue
                queue.remove(allocationOld);
                queue.addLast(allocationOld);
                return allocationOld;
            }
        }
        return null;
    }

    public String getReg(String id) {
        StringBuilder sb = new StringBuilder();
        String register = String.format("v%d", queue.size() + 1);
        Integer offset = GlobalOffsetTable.methodOffsetTable.getOrDefault(
            id, 9999);

        if (queue.size() < MAX_SIZE) {
            queue.addLast(new AllocationOld(
                register,
                id
            ));
        } else {
            // cache full have to evict
            AllocationOld toEvict = queue.pop();
            queue.addLast(new AllocationOld(toEvict.getRegister(), id));
            register = toEvict.getRegister();
            sb.append(String.format(
                "    str %s, [fp, #%d]\n",
                register,
                GlobalOffsetTable.methodOffsetTable.getOrDefault(
                    toEvict.getId(), 9991)));
        }


        sb.append(String.format(
            "    ldr %s, [fp, #%d]\n",
            register, offset

        ));

        return sb.toString();
    }

    public void flush() {
        queue.clear();
    }


}
