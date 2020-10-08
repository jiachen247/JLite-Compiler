package main.java.staticcheckers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.Node;

public abstract class Checker {
    private String name;
    private List<CheckError> errors;

    public Checker(String name) {
        this.name = name;
        errors = new ArrayList<>();
    }
    public abstract boolean isOK();

    public void reportError(Node node, String error) {
        String format = "[%s] Error at (%d, %d): %s";
        errors.add(new CheckError(
            // x and y are 0 indexed
            node.x, node.y, String.format(format, name, node.x + 1, node.y + 1, error)
        ));
    }

    // print sorted by line
    public void printErrors() {
        for (CheckError e: errors.stream().sorted().collect(Collectors.toList())) {
            System.out.println(e);
        }
        System.out.println(String.format("%s failed with %d errors. Please fix before proceeding!", name, errors.size()));
    }
}
