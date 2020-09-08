package main.java.ast.common;

import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static <T> String listToString(List<T> list) {
        return list.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
    }

    public static <T> String joinWithNewLine(List<T> list) {
        return list.stream().map(i -> i.toString() + "\n").collect(Collectors.joining(""));
    }

    public static String indent(String s) {
        return s.replaceAll("(?m)^", "\t");
    }
}
