package main.java.staticcheckers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.ClassDecl;
import main.java.parsetree.MainClass;
import main.java.parsetree.MdDecl;
import main.java.parsetree.MdSignature;
import main.java.parsetree.Program;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.VarDecl;
import main.java.parsetree.statement.Statement;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
import main.java.staticcheckers.type.Environment;
import main.java.staticcheckers.type.LocalEnvironment;
import main.java.staticcheckers.type.FunctionType;

public class TypeChecker implements Checker {
    private HashMap<BasicType, ClassDescriptor> classDescriptors = new HashMap<>();
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
                Environment e = new Environment(classDescriptors, classDescriptors.get(BasicType.fromType(classDecl.type)), localEnv);
                for (Statement stmt : mdDecl.mdBody.stmts) {
                    if (stmt.typeCheck(e).equals(BasicType.ERROR_TYPE)) {
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
        classDescriptors.put(new BasicType("Main"), buildClassDescriptor(program.getMainClass()));
        classDescriptors.put(BasicType.INT_TYPE, new ClassDescriptor(BasicType.INT_TYPE));
        classDescriptors.put(BasicType.BOOL_TYPE, new ClassDescriptor(BasicType.BOOL_TYPE));
        classDescriptors.put(BasicType.STRING_TYPE, new ClassDescriptor(BasicType.STRING_TYPE));
        classDescriptors.put(BasicType.VOID_TYPE, new ClassDescriptor(BasicType.VOID_TYPE));
        classDescriptors.put(BasicType.CHAR_TYPE, new ClassDescriptor(BasicType.CHAR_TYPE));
        classDescriptors.put(BasicType.NULL_TYPE, new ClassDescriptor(BasicType.NULL_TYPE));

        for (ClassDecl classDecl : program.getClassDeclList()) {
            classDescriptors.put(BasicType.fromType(classDecl.type), buildClassDescriptor(classDecl));
        }
    }

    private ClassDescriptor buildClassDescriptor(MainClass mainClass) {
        HashMap<MdSignature, FunctionType> methods = new HashMap<>();

        List<BasicType> parameters = mainClass.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
        MdSignature mainSig = new MdSignature(new Id("main"), parameters);
        FunctionType mainFunction = new FunctionType(parameters, BasicType.VOID_TYPE);
        methods.put(mainSig, mainFunction);
        return new ClassDescriptor("Main", new HashMap<>(), methods);
    }

    private ClassDescriptor buildClassDescriptor(ClassDecl classDecl) {
        HashMap<Id, BasicType> fields = new HashMap<>();
        HashMap<MdSignature, FunctionType> methods = new HashMap<>();

        for (VarDecl var : classDecl.getVarDeclList()) {
            fields.put(var.id, BasicType.fromType(var.type));
        }

        for (MdDecl md : classDecl.getMdDeclList()) {
            List<BasicType> parameters = md.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
            FunctionType functionType = new FunctionType(parameters, md.getReturnType());
            methods.put(md.signature, functionType);
        }

        return new ClassDescriptor(classDecl.type.getName(), fields, methods);
    }

    private LocalEnvironment buildLocalEnv(MdDecl md) {
        HashMap<Id, BasicType> local = new HashMap<>();

        for (Argument arg : md.arguments) {
            if (local.containsKey(arg.id)) {
                // shouldnt hit due to distinct name checking
                System.out.println("Found redeclaration in method");
            } else {
                local.put(arg.id, BasicType.fromType(arg.type));
            }
        }

        for (VarDecl var : md.getMdBody().variableDeclarations) {
            if (local.containsKey(var.id)) {
                // shouldnt hit due to distinct name checking
                System.out.println("Found redeclaration in method");
            } else {
                local.put(var.id, BasicType.fromType(var.type));
            }
        }

        return new LocalEnvironment(local);
    }

    private void error(String e) {
        System.out.println("[TypeChecker] " + e);
    }


}
