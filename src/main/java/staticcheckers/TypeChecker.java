package main.java.staticcheckers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.ClassDecl;
import main.java.parsetree.MainClass;
import main.java.parsetree.MdDecl;
import main.java.parsetree.Program;
import main.java.parsetree.shared.VarDecl;
import main.java.parsetree.statement.Statement;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
import main.java.staticcheckers.type.Environment;
import main.java.staticcheckers.type.LocalEnvironment;
import main.java.staticcheckers.type.FunctionType;

public class TypeChecker implements Checker {
    private HashMap<String, ClassDescriptor> classDescriptors = new HashMap<>();
    private Program program;
    public TypeChecker(Program program) {
        this.program = program;
    }

    @Override
    public boolean isOK() {
        initialize();
        // do all the checks
        classDescriptors.forEach((k,v) -> System.out.println("key: "+k+" value:"+v));

        boolean isValid = true;

        for (ClassDecl classDecl : program.getClassDeclList()) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                LocalEnvironment localEnv = buildLocalEnv(mdDecl);
                Environment e = new Environment(classDescriptors, localEnv);
                for (Statement stmt : mdDecl.mdBody.stmts) {
                    if (stmt.typeCheck(e)) {
                        error("Failed to validate type for '" + stmt + "'");
                        isValid = false;
                    }
                }
            }
        }


        return isValid;
    }

    // Build class descriptor Environments
    private void initialize() {
        // build class Descriptors
        classDescriptors.put("Main", buildClassDescriptor(program.getMainClass()));

        for (ClassDecl classDecl : program.getClassDeclList()) {
            classDescriptors.put(classDecl.type.getName(), buildClassDescriptor(classDecl));
        }
    }

    private ClassDescriptor buildClassDescriptor(MainClass mainClass) {
        HashMap<String, FunctionType> methods = new HashMap<>();
        List<BasicType> parameters = mainClass.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
        FunctionType mainFunction = new FunctionType(parameters, BasicType.VOID_TYPE);
        methods.put("main", mainFunction);
        return new ClassDescriptor("Main", new HashMap<>(), methods);
    }

    private ClassDescriptor buildClassDescriptor(ClassDecl classDecl) {
        HashMap<String, BasicType> fields = new HashMap<>();
        HashMap<String, FunctionType> methods = new HashMap<>();

        for (VarDecl var : classDecl.getVarDeclList()) {
            fields.put(var.id.name, BasicType.fromType(var.type));
        }

        for (MdDecl md : classDecl.getMdDeclList()) {
            List<BasicType> parameters = md.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
            FunctionType functionType = new FunctionType(parameters, BasicType.fromType(md.signature.returnType));
            methods.put(md.signature.id.name, functionType);
        }

        return new ClassDescriptor(classDecl.type.getName(), fields, methods);
    }

    private LocalEnvironment buildLocalEnv(MdDecl md) {
        // check that types are valid as well
        return new LocalEnvironment();
    }

    private void error(String e) {
        System.out.println("[TypeChecker] " + e);
    }


}
