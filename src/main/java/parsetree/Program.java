package main.java.parsetree;

import java.util.*;
import java.util.stream.Collectors;
import main.java.parsetree.shared.Helper;

public class Program implements Node {

    private final MainClass mainClass;

    public MainClass getMainClass() {
        return mainClass;
    }

    public LinkedList<ClassDecl> getClassDeclList() {
        return classDeclList;
    }

    private final LinkedList<ClassDecl> classDeclList;

    public Program(MainClass mainClass, LinkedList<ClassDecl> classDeclList) {
        this.mainClass = mainClass;
        this.classDeclList = classDeclList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(mainClass.toString() + "\n");

        for (ClassDecl classDecl : classDeclList) {
            sb.append(classDecl.toString() + "\n");
        }

        return sb.toString();
    }
}
