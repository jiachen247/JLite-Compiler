package main.java.parsetree;

import java.util.*;
import java.util.stream.Collectors;
import main.java.parsetree.shared.Helper;

public class Program extends Node {

    private final MainClass mainClass;

    public MainClass getMainClass() {
        return mainClass;
    }

    public LinkedList<ClassDecl> getClassDeclList() {
        return classDeclList;
    }

    private final LinkedList<ClassDecl> classDeclList;

    public Program(int x, int y, MainClass mainClass, LinkedList<ClassDecl> classDeclList) {
        super(x, y);
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
