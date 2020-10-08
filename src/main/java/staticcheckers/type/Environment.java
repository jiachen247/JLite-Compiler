package main.java.staticcheckers.type;

import java.util.HashMap;

import main.java.parsetree.shared.Id;

public class Environment {
    public Environment(HashMap<BasicType, ClassDescriptor> classDescriptors, ClassDescriptor classContext, LocalEnvironment localEnvironment) {
        this.classDescriptors = classDescriptors;
        this.localEnvironment = localEnvironment;
        this.classContext = classContext;

    }

    private HashMap<BasicType, ClassDescriptor> classDescriptors;
    private LocalEnvironment localEnvironment;

    public ClassDescriptor getClassContext() {
        return classContext;
    }

    private ClassDescriptor classContext;

    public HashMap<BasicType, ClassDescriptor> getClassDescriptors() {
        return classDescriptors;
    }

    public LocalEnvironment getLocalEnvironment() {
        return localEnvironment;
    }

    public BasicType lookup(Id id) {
        if (localEnvironment.local.containsKey(id)) {
            return localEnvironment.local.get(id);
        } else if (classContext.getFields().containsKey(id)) {
            return classContext.getFields().get(id);
        } else {
            return BasicType.ERROR_TYPE;
        }
    }
}
