package main.java.staticcheckers;

import java.util.*;
import java.util.stream.Collectors;

import main.java.parsetree.ClassDecl;
import main.java.parsetree.MdDecl;
import main.java.parsetree.MdSignature;
import main.java.parsetree.Program;

/**
 * The type Name checker.
 */
public class NameChecker implements Checker {

    private Program program;

    public NameChecker(Program program) {
        this.program = program;
    }

    @Override
    public boolean isOK() {
        boolean uniqueClassNames = checkForUniqueClassNames();
        boolean uniqueClassFields = checkForUniqueClassFields();
        boolean uniqueMethodParameters = checkForUniqueMethodParameters();
        boolean uniqueMethodSignatures = checkForUniqueMethodSignatures();
        boolean uniqueVariablesInMethod = checkForUniqueFieldsInMethods();

        return uniqueClassNames
            && uniqueClassFields
            && uniqueMethodParameters
            && uniqueMethodSignatures
            && uniqueVariablesInMethod;
    }

    /*
        (a) Checks if all fields in class are unique, both class variables and methods.
     */
    private boolean checkForUniqueClassFields() {
        boolean isValid = true;
        for (ClassDecl classDecl : program.getClassDeclList()) {
            List<String> varNames = classDecl.getVarDeclList()
                .stream()
                .map(var -> var.id.name)
                .collect(Collectors.toList());

            if (!areDistinct(varNames)) {
                error("Found duplicate class variable name. " + varNames);
                isValid = false;
            }

            // This is actually allowed in java
//            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
//                if (varNames.contains(mdDecl.getSignature().id.name)) {
//                    error("Variable and function within a class cannot share the same name. " + mdDecl.getSignature().id.name);
//                    isValid = false;
//                }
//            }
        }
        return isValid;
    }

    /*
        (b) Checks for duplicate class names
    */
    private boolean checkForUniqueClassNames() {
        List<String> names = program.getClassDeclList()
            .stream()
            .map(c -> c.getType().getName())
            .collect(Collectors.toList());

        boolean isValid = true;

        if (names.contains("Main")) {
            error("Found duplicate main class.");
            isValid = false;
        }

        if (!areDistinct(names)) {
            // find out which class
            error("Found duplicate class name.");
            isValid = false;
        }

        return isValid;
    }

    /*
        (c) Checks for duplicate parameters names in method declaration
    */
    private boolean checkForUniqueMethodParameters() {
        boolean isValid = true;
        for (ClassDecl classDecl : program.getClassDeclList()) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                List<String> parameterNames = mdDecl
                    .getArguments()
                    .stream()
                    .map(arg -> arg.id.name)
                    .collect(Collectors.toList());

                if (!areDistinct(parameterNames)) {
                    error("Found duplicate parameter names. " + parameterNames);
                    isValid = false;
                }
            }
        }
        return isValid;
    }


    /*
        (d) Checks for duplicate method signatures within a class
    */
    private boolean checkForUniqueMethodSignatures() {
        boolean isValid = true;
        for (ClassDecl classDecl : program.getClassDeclList()) {

            List<MdSignature> signatures = classDecl.getMdDeclList()
                .stream()
                .map(md -> md.signature)
                .collect(Collectors.toList());

            for (int i = 0; i < signatures.size() - 1; i++) {
                for (int j = i + 1; j < signatures.size(); j++) {
                    if (signatures.get(i).equals(signatures.get(j))) {
                        error("Failed to overlaod, found duplicate method signature.");
                        isValid = false;
                    }
                }
            }
        }

        return isValid;
    }

    /*
        (e) (Added) check for duplicate within parameters and method variables
        Check for conflicts in parameters and method variable decl and method names to prevent
        Void method(Int a) {
            Int a;
            Int a(Int a) {
            }
        }
    */
    private boolean checkForUniqueFieldsInMethods() {
        boolean isValid = true;
        for (ClassDecl classDecl : program.getClassDeclList()) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                // Check duplicates in parameters and var decl
                List<String> parametersNames = mdDecl
                    .getArguments()
                    .stream()
                    .map(param -> param.id.name)
                    .collect(Collectors.toList());

                List<String> varNames = mdDecl.getMdBody().variableDeclarations
                    .stream()
                    .map(var -> var.id.name)
                    .collect(Collectors.toList());

                ArrayList combined = new ArrayList(parametersNames);
                combined.addAll(varNames);

                if (!areDistinct(combined)) {
                    error("Variable has already been declared in method parameters.");
                    isValid = false;
                }
            }
        }

        return isValid;
    }

    private void error(String e) {
        System.out.println("[NameChecker]: " + e);
    }

    // check if list contains distinct / unique objects
    public <T>  boolean areDistinct(List<T> list) {
        return list.size() == (new HashSet(list)).size();
    }
}
