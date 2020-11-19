package main.java.ir3.stmt;

import java.util.List;

import main.java.ir3.exp.Id3;

public interface Stmt3 {
    String generateArm();
    List<Id3> getUses();
    Id3 getDef();
}
