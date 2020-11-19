package main.java.arm;

import java.util.LinkedList;

public class RegisterAllocator {
    private LinkedList<Allocation> queue;
    private int MAX_SIZE = 5;

    public RegisterAllocator() {
        this.queue = new LinkedList<>();
    }

    public boolean contains(String varname) {
        for (Allocation allocation: queue) {
            if (allocation.getId().equals(varname)) {
                return true;
            }
        }
        return false;
    }

    public Allocation getAllocation(String varname) {

        for (Allocation allocation: queue) {
            if (allocation.getId().equals(varname)) {
                // push allocation to the back of the queue
                queue.remove(allocation);
                queue.addLast(allocation);
                return allocation;
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
            queue.addLast(new Allocation(
                register,
                id
            ));
        } else {
            // cache full have to evict
            Allocation toEvict = queue.pop();
            queue.addLast(new Allocation(toEvict.getRegister(), id));
            register = toEvict.getRegister();
            sb.append(String.format(
                "    str %s, [fp, #%d]\n",
                register,
                GlobalOffsetTable.methodOffsetTable.getOrDefault(
                    toEvict.getId(), 9999)));
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
