package main.java.ast;

import java.util.LinkedList;


public class Program implements Node {

    private final MainClass mainClass;
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
