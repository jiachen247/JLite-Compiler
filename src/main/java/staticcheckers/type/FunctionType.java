package main.java.staticcheckers.type;

import java.util.List;

import main.java.parsetree.shared.Type;

public class FunctionType {
    private List<BasicType> parametersType;
    private BasicType returnType;

    public FunctionType(List<BasicType> parametersType, BasicType returnType) {
        this.parametersType = parametersType;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        return "FunctionType{" +
            "parametersType=" + parametersType +
            ", returnType=" + returnType +
            '}';
    }
}
