package main.java.ir3;

import java.util.concurrent.atomic.AtomicLong;

public class LabelGenerator {
    private static AtomicLong counter = new AtomicLong(0);

    public static Label getLabel() {
        return new Label(counter.addAndGet(1));
    }
}
