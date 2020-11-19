package main.java.ir3;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
