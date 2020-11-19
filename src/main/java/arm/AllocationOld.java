package main.java.arm;

public class AllocationOld {
    public AllocationOld(String register, String id) {
        this.register = register;
        this.id = id;
    }

    public String getRegister() {
        return register;
    }

    public String getId() {
        return id;
    }

    private String register; // v1-5
    private String id;
}
