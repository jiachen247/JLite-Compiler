package main.java.ast.common;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    private static Helper _instance = null;

    private Helper() {

    }

    public static Helper getInstance() {
        if (_instance == null)
            _instance = new Helper();

        return _instance;
    }

    public <T> String concat(List<T> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public <T> String join(List<T> list) {
        return list.stream().map(i -> i.toString() + "\n").collect(Collectors.joining(""));
    }

    public String indent(String s) {
        return s.replaceAll("(?m)^", "\t");
    }
}
