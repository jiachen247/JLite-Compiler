package main.java.staticcheckers;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.ClassDecl;
import main.java.parsetree.MdDecl;
import main.java.parsetree.MdSignature;
import main.java.parsetree.Program;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.VarDecl;

/**
 * The type Name checker.
 */
public class NameChecker extends Checker {

    private Program program;

    public NameChecker(Program program) {
        super("DistinctNameCheck");
        this.program = program;
    }

    @Override
    public boolean isOK() {
        boolean uniqueClassNames = checkForUniqueClassNames();
        boolean uniqueClassFields = checkForUniqueClassFields();
        boolean uniqueMethodParameters = checkForUniqueMethodClassScope();
        boolean uniqueMethodSignatures = checkForUniqueMethodSignatures();

        return uniqueClassNames
            && uniqueClassFields
            && uniqueMethodParameters
            && uniqueMethodSignatures;
    }

    /*
        (a) Checks if all fields in class are unique, (java actually allows class methods and names to be the same since they are treated differently)
        Since the specification is ambiguous about this, we will go ahead an allow this to be consistent with Java.
        class Test {
            int a;
            void a() {
                return ;
            }
        }

     */
    private boolean checkForUniqueClassFields() {
        boolean isValid = true;
        for (ClassDecl classDecl : program.getClassDeclList()) {
            HashSet<String> seen = new HashSet();

            for (VarDecl varDecl : classDecl.getVarDeclList()) {
                String varname = varDecl.id.name;

                if (seen.contains(varname)) {
                    reportError(varDecl.id, String.format("Variable `%s` has already been declared previously.", varDecl.id));
                    isValid = false;
                } else {
                    seen.add(varname);
                }
            }

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

        HashSet<String> seen = new HashSet<>();

        for (ClassDecl classDecl : program.getClassDeclList()) {
            String cname = classDecl.type.getName();

            if (cname.equals("Main")) {
                reportError(classDecl.type, "Only one `Main` class is allowed.");
                isValid = false;
            } else if (seen.contains(cname)) {
                reportError(classDecl.type, String.format("Class `%s` has already been declared previously.", cname));
                isValid = false;
            } else {
                seen.add(cname);
            }
        }

        return isValid;
    }

    /*
        (c) Checks for duplicate parameters names in method declaration and field declarations
    */
    private boolean checkForUniqueMethodClassScope() {
        boolean isValid = true;

        for (ClassDecl classDecl : program.getClassDeclList()) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                HashSet<String> seen = new HashSet<>();
                for (Argument arg : mdDecl.getArguments()) {
                    String argname = arg.id.name;
                    if (seen.contains(argname)) {
                        reportError(arg.id, String.format("Parameter name `%s` has already been declared earlier for another function parameter.", argname));
                        isValid = false;
                    } else {
                        seen.add(argname);
                    }
                }

                for (VarDecl varDecl : mdDecl.getMdBody().variableDeclarations) {
                    String varname = varDecl.id.name;
                    if (seen.contains(varname)) {
                        reportError(varDecl.id, String.format("Variable `%s` has already been declared earlier in another parameter or field.", varname));
                        isValid = false;
                    } else {
                        seen.add(varname);
                    }
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
            HashSet<MdSignature> seen = new HashSet();
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                MdSignature sig = mdDecl.signature;

                if (seen.contains(sig)) {
                    reportError(mdDecl.signature.id,
                        String.format("Failed to overload method `%s`, method signature `%s` already exist in class.",
                            mdDecl.signature.id.name,
                            mdDecl.signature.toString()));
                    isValid = false;
                } else {
                    seen.add(sig);
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
//    private boolean checkForUniqueFieldsInMethods() {
//        boolean isValid = true;
//        for (ClassDecl classDecl : program.getClassDeclList()) {
//            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
//                // Check duplicates in parameters and var decl
//                List<String> parametersNames = mdDecl
//                    .getArguments()
//                    .stream()
//                    .map(param -> param.id.name)
//                    .collect(Collectors.toList());
//
//                List<String> varNames = mdDecl.getMdBody().variableDeclarations
//                    .stream()
//                    .map(var -> var.id.name)
//                    .collect(Collectors.toList());
//
//                ArrayList combined = new ArrayList(parametersNames);
//                combined.addAll(varNames);
//
//                if (!areDistinct(combined)) {
//                    error("Variable has already been declared in method parameters.");
//                    isValid = false;
//                }
//            }
//        }
//
//        return isValid;
//    }
//
//    // check if list contains distinct / unique objects
//    public <T>  boolean areDistinct(List<T> list) {
//        return list.size() == (new HashSet(list)).size();
//    }
}
