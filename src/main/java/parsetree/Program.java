package main.java.parsetree;

import java.util.LinkedList;
import java.util.List;

import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

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
