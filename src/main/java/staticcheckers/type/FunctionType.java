package main.java.staticcheckers.type;

import java.util.List;

public class FunctionType {
    public String methodId;

    public List<BasicType> getParametersType() {
        return parametersType;
    }

    public BasicType getReturnType() {
        return returnType;
    }

    private List<BasicType> parametersType;
    private BasicType returnType;

    public FunctionType(List<BasicType> parametersType, BasicType returnType, String methodId) {
        this.parametersType = parametersType;
        this.returnType = returnType;
        this.methodId = methodId;
    }

    @Override
    public String toString() {
        return "FunctionType{" +
            "parametersType=" + parametersType +
            ", returnType=" + returnType +
            '}';
    }
}
