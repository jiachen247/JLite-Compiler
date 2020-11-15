package main.java.ir3.stmt;

import main.java.ir3.Program3;

public class EmptyReturnStatement3 implements Stmt3 {
    @Override
    public String toString() {
        return " Return ;";
    }

    @Override
    public String generateArm() {
        return String.format("    b %s_exit\n", Program3.getCurrentMethod());
    }
}
