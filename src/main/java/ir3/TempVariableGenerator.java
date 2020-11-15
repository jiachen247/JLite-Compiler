package main.java.ir3;

import java.util.concurrent.atomic.AtomicLong;

import main.java.ir3.exp.Id3;
import main.java.staticcheckers.type.BasicType;

public class TempVariableGenerator {
    private static AtomicLong counter = new AtomicLong(0);

    public static Id3 getId(BasicType type) {
        return new Id3(String.format("_t%d", counter.addAndGet(1)), type);
    }
}
