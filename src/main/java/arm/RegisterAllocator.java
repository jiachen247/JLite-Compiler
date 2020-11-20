package main.java.arm;

public class RegisterAllocator {
    private Allocation allocation;

    public RegisterAllocator(Allocation allocation) {
        this.allocation = allocation;
    }

    public boolean contains(String id) {
        return allocation.contains(id);
    }

    public Allocation getAllocation(String id) {
        return allocation;
    }

    public String getReg(String id) {
        return allocation.lookup(id);
    }
}
