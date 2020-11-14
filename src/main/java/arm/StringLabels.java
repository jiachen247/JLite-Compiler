package main.java.arm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StringLabels {
    private static StringLabels _this = null;
    private HashMap<String, Integer> hm;
    private StringBuilder labels;
    private Integer count;

    public static final String intLabel = "_int";
    public static final String stringLabel = "_string";
    public static final String trueLabel = "_true";
    public static final String falseLabel = "_false";
    public static final String nullLabel = "_null";

    public static final String intFormat = "%d";
    public static final String stringFormat = "%s";
    public static final String trueFormat = "true";
    public static final String falseFormat = "false";
    public static final String nullFormat = "null";

    private final String format = "%s:\n    .asciz \"%s\"\n";

    private StringLabels() {
        hm = new HashMap<>();
        labels = new StringBuilder();
        count = 1;

        labels.append(String.format(format, intLabel, intFormat));
        labels.append(String.format(format, stringLabel, stringFormat));
        labels.append(String.format(format, trueLabel, trueFormat));
        labels.append(String.format(format, falseLabel, falseFormat));
        labels.append(String.format(format, nullLabel, nullFormat));
    }
    public static StringLabels getInstance() {
        if (_this == null) {
            _this = new StringLabels();
        }
        return _this;
    }
    private String formatLabel(Integer i) {
        return String.format("S%d", i);
    }

    public String getLabel(String string) {
        if (!hm.containsKey(string)) {
            hm.put(string, count);
            labels.append(String.format(format, formatLabel(count), string));
            count++;
        }

        return formatLabel(hm.get(string));
    }

    @Override
    public String toString() {
        return labels.toString();
    }
}
