package main.java.ir3;

import java.util.List;

import main.java.arm.ClassOffsetTable;
import main.java.arm.GlobalOffsetTable;
import main.java.arm.StringLabels;

public class Program3 {
    private List<CData3> classes;
    private List<CMtd3> methods;

    public static String getCurrentClass() {
        return currentClass;
    }

    private static String currentClass = "";
    private static String currentMethod = "";

    public static String getCurrentMethod() {
        return currentMethod;
    }

    public Program3(List<CData3> cdata, List<CMtd3> cmtd) {
        this.classes = cdata;
        this.methods = cmtd;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("======= Start of IR3 Program =======\n");
        sb.append("======= CData3 =======\n\n");
        for (CData3 cd : classes) {
            sb.append(cd.toString()).append("\n");
        }

        sb.append("======= CMtd3 =======\n");

        for (CMtd3 mtd : methods) {
            sb.append(mtd.toString()).append("\n");
        }
        sb.append("======= End of IR3 Program =======");
        return sb.toString();
    }

    public String generateArm(boolean toOptimize, boolean debug) {
        StringBuilder body = new StringBuilder();

        ClassOffsetTable.getInstance().init(classes);

        if (debug) {
            ClassOffsetTable.getInstance().print();
        }


        for (CMtd3 method : methods) {
            currentMethod = method.getId().name;

            if (method.getIsMain()) {
                // Main_0 to Main
                String methodName = method.getId().name;
                currentClass = methodName.substring(0, methodName.length() - 2);
            } else {
                // get type of 'this' in first argument
                currentClass = method.getArguments().get(0).getType().getName();
            }

            GlobalOffsetTable.getInstance().setMethodOffsetTable(method.getOffsetTable());
            body.append(method.generateArm(toOptimize, classes, debug));
        }

        return String.format("    .data\n" +
                "%s\n" +
                "    .text\n" +
                "    .global main\n" +
                "    .type main, %%function\n\n" +
                "%s" +
                "    .end\n",
            StringLabels.getInstance().toString(),
            body.toString());
    }
}
