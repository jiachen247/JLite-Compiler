package main.java.staticcheckers.type;

import java.util.HashMap;
import java.util.List;

public class Environment {
    public Environment(HashMap<String, ClassDescriptor>  classDescriptors, LocalEnvironment localEnvironment) {
        this.classDescriptors = classDescriptors;
        this.localEnvironment = localEnvironment;
    }

    private HashMap<String, ClassDescriptor> classDescriptors;
    private LocalEnvironment localEnvironment;

    public HashMap<String, ClassDescriptor>  getClassDescriptors() {
        return classDescriptors;
    }

    public LocalEnvironment getLocalEnvironment() {
        return localEnvironment;
    }
}
