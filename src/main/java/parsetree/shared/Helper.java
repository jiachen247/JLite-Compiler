package main.java.parsetree.shared;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import main.java.parsetree.statement.Statement;
import main.java.staticcheckers.CheckError;
import main.java.staticcheckers.type.BasicType;
import main.java.staticcheckers.type.Environment;

public class Helper {
    private static Helper _instance = null;

    private Helper() {

    }

    public static Helper getInstance() {
        if (_instance == null)
            _instance = new Helper();

        return _instance;
    }

    public <T> String concat(List<T> list) {
        return list.stream().map(Object::toString).collect(Collectors.joining(", "));
    }

    public <T> String join(List<T> list) {
        return list.stream().map(i -> i.toString() + "\n").collect(Collectors.joining(""));
    }

    public String indent(String s) {
        return s.replaceAll("(?m)^", "\t");
    }

    public BasicType evalBlock(Environment env, LinkedList<Statement> stmts, List<CheckError> errors) {
        BasicType ret = BasicType.VOID_TYPE;
        boolean isValid = true;
        for (Statement stmt : stmts) {
            ret = stmt.typeCheck(env, errors);
            if (ret.equals(BasicType.ERROR_TYPE)) {
                isValid = false;
            }
        }

        return isValid ? ret : BasicType.ERROR_TYPE;
    }
}
