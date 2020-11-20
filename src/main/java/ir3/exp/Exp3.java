package main.java.ir3.exp;

import java.util.List;

import main.java.staticcheckers.type.BasicType;

public interface Exp3 {
    String generateArm(String target);

    BasicType getType();

    List<Id3> getUses();
}
