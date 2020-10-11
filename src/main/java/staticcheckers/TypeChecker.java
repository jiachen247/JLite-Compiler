package main.java.staticcheckers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.tools.javac.Main;
import main.java.parsetree.ClassDecl;
import main.java.parsetree.MainClass;
import main.java.parsetree.MdDecl;
import main.java.parsetree.MdSignature;
import main.java.parsetree.Program;
import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Id;
import main.java.parsetree.shared.Type;
import main.java.parsetree.shared.VarDecl;
import main.java.parsetree.statement.Statement;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.ClassDescriptor;
import main.java.staticcheckers.type.Environment;
import main.java.staticcheckers.type.FunctionType;
import main.java.staticcheckers.type.LocalEnvironment;

public class TypeChecker extends Checker {
    private HashMap<BasicType, ClassDescriptor> classDescriptors = new HashMap<>();
    private Program program;
    private static List<CheckError> errors = new ArrayList<>();

    public TypeChecker(Program program) {
        super("TypeCheck");
        this.program = program;
    }

    @Override
    public boolean isOK() {
        initialize();

        boolean isValid = true;

        // Type check main class body
        LocalEnvironment mainLocalEnv = buildLocalEnv(program.getMainClass());
        ClassDescriptor mainClassContext = classDescriptors.get(new BasicType("Main"));
        Environment mainEnv = new Environment(classDescriptors, mainClassContext, mainLocalEnv);
        for (Statement stmt : program.getMainClass().body.stmts) {
            System.out.println("main encjbneajkc");
            if (stmt.typeCheck(mainEnv, errors).equals(BasicType.ERROR_TYPE)) {
                // error("Failed to validate type for '" + stmt + "'");
                isValid = false;
            }
        }

        for (ClassDecl classDecl : program.getClassDeclList()) {
            for (MdDecl mdDecl : classDecl.getMdDeclList()) {
                LocalEnvironment localEnv = buildLocalEnv(mdDecl);
                ClassDescriptor classContext = classDescriptors.get(BasicType.fromType(classDecl.type));
                Environment enclosingEnv = new Environment(classDescriptors, classContext, localEnv);

                // check return type of methods
                if (!classDescriptors.containsKey(mdDecl.returnType)) {
                    errors.add(TypeChecker.buildTypeError(mdDecl.x, mdDecl.y,
                        String.format("Method return type `%s` does not exist.", mdDecl.returnType)));
                }


                for (Statement stmt : mdDecl.mdBody.stmts) {
                    System.out.println("senvsjenvjsn");
                    if (stmt.typeCheck(enclosingEnv, errors).equals(BasicType.ERROR_TYPE)) {
                        // error("Failed to validate type for '" + stmt + "'");
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
        classDescriptors.put(new BasicType(program.getMainClass().type.getName()), buildClassDescriptor(program.getMainClass()));
        classDescriptors.put(BasicType.INT_TYPE, new ClassDescriptor(BasicType.INT_TYPE));
        classDescriptors.put(BasicType.BOOL_TYPE, new ClassDescriptor(BasicType.BOOL_TYPE));
        classDescriptors.put(BasicType.STRING_TYPE, new ClassDescriptor(BasicType.STRING_TYPE));
        classDescriptors.put(BasicType.VOID_TYPE, new ClassDescriptor(BasicType.VOID_TYPE));
        classDescriptors.put(BasicType.NULL_TYPE, new ClassDescriptor(BasicType.NULL_TYPE));

        for (ClassDecl classDecl : program.getClassDeclList()) {
            classDescriptors.put(BasicType.fromType(classDecl.type), buildClassDescriptor(classDecl));
        }
    }

    private ClassDescriptor buildClassDescriptor(MainClass mainClass) {
        HashMap<MdSignature, FunctionType> methods = new HashMap<>();

        List<BasicType> parameters = mainClass.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
        MdSignature mainSig = new MdSignature(mainClass.x, mainClass.y, new Id(mainClass.type.x, mainClass.type.y, "main"), parameters);
        FunctionType mainFunction = new FunctionType(parameters, BasicType.VOID_TYPE, mainClass.methodId);
        methods.put(mainSig, mainFunction);
        return new ClassDescriptor(mainClass.type.getName(), new HashMap<>(), methods);
    }

    private ClassDescriptor buildClassDescriptor(ClassDecl classDecl) {
        HashMap<Id, BasicType> fields = new HashMap<>();
        HashMap<MdSignature, FunctionType> methods = new HashMap<>();

        for (VarDecl var : classDecl.getVarDeclList()) {
            fields.put(var.id, BasicType.fromType(var.type));
        }

        for (MdDecl md : classDecl.getMdDeclList()) {
            List<BasicType> parameters = md.arguments.stream().map(arg -> BasicType.fromType(arg.type)).collect(Collectors.toList());
            FunctionType functionType = new FunctionType(parameters, md.getReturnType(), md.getUniqueMethodId());
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

        return new LocalEnvironment(local, md.returnType);
    }

    private LocalEnvironment buildLocalEnv(MainClass main) {
        HashMap<Id, BasicType> local = new HashMap<>();

        for (Argument arg : main.arguments) {
            if (local.containsKey(arg.id)) {
                // shouldnt hit due to distinct name checking
                System.out.println("Found redeclaration in method");
            } else {
                local.put(arg.id, BasicType.fromType(arg.type));
            }
        }

        for (VarDecl var : main.body.variableDeclarations) {
            if (local.containsKey(var.id)) {
                // shouldnt hit due to distinct name checking
                System.out.println("Found redeclaration in method");
            } else {
                local.put(var.id, BasicType.fromType(var.type));
            }
        }

        return new LocalEnvironment(local,BasicType.fromType(main.type));
    }

    @Override
    public void printErrors() {
        for (CheckError e : errors.stream().sorted().collect(Collectors.toList())) {
            System.out.println(e);
        }
        System.out.println(String.format("%s failed with %d errors. Please fix before proceeding!", "TypeCheck", errors.size()));

    }

    public static CheckError buildTypeError(int x, int y, String err) {
        return new CheckError(x, y, String.format("[TypeCheck] Error at (%d, %d): %s", x + 1, y + 1, err));
    }


}
