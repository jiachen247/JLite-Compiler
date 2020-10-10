package main.java.parsetree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import main.java.ir3.CData3;
import main.java.ir3.CMtd3;
import main.java.ir3.Program3;

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

    public Program3 toProgram3() {
        List<CData3> cdata = new ArrayList<>();
        List<CMtd3> cmtd = new ArrayList<>();

        cdata.add(mainClass.toCData3());

        for (ClassDecl classDecl : classDeclList) {
            cdata.add(classDecl.toCData3());
        }

        for (ClassDecl classDecl : classDeclList) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                cmtd.add(mdDecl.toCMd3(classDecl));
            }
        }


        return new Program3(cdata, cmtd);
    }

    public void assignMethodNumbers() {

        for (ClassDecl classDecl : classDeclList) {
            //
            long index = 0;

            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                mdDecl.setUniqueClassMethodIndex(index);
                index += 1;
            }
        }
    }
}
