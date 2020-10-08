package main.java.staticcheckers;

public class CheckError implements Comparable<CheckError> {
    private int x;
    private int y;

    public CheckError(int x, int y, String errString) {
        this.x = x;
        this.y = y;
        this.errString = errString;
    }

    private String errString;

    @Override
    public String toString() {
        return errString;
    }

    @Override
    public int compareTo(CheckError o) {
        if (this.x == o.x) {
            return this.y - o.y;
        } else {
            return this.x - o.x;
        }
    }
}
