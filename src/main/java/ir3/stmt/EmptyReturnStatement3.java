package main.java.ir3.stmt;

public class EmptyReturnStatement3 implements Stmt3 {
    @Override
    public String toString() {
        return " Return ;";
    }

    @Override
    public String generateArm() {
        return "todo impl return";
    }

}
