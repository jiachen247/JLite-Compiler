package main.java.ir3;

public class Label {
    public long getId() {
        return id;
    }

    private long id;

    public Label(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%d", id);
    }

    public String generateArm() {
        return String.format("%s:\n", getName());
    }

    public String getName() {
        return String.format("l%d", id);
    }
}
