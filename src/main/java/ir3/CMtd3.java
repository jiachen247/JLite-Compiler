package main.java.ir3;

import java.util.HashMap;
import java.util.List;

import main.java.parsetree.shared.Argument;
import main.java.parsetree.shared.Helper;
import main.java.parsetree.shared.Id;
import main.java.staticcheckers.type.BasicType;

public class CMtd3 {
    private BasicType type;

    public Id getId() {
        return id;
    }

    private Id id;
    private List<Argument> arguments;
    private MdBody3 body;

    public HashMap<String, Integer> offsetTable;

    public CMtd3(BasicType type, Id id, List<Argument> arguments, MdBody3 body) {
        this.type = type;
        this.id = id;
        this.arguments = arguments;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("%s %s(%s) {\n%s}\n",
            type.toString(),
            id.toString(),
            Helper.getInstance().concat(arguments),
            body.toString());
    }

    public String generateArm(boolean toOptimize, List<CData3> classes) {
        String entryLabel = id.toString();
        String exitLabel = String.format("%s_exit", entryLabel);
        if (entryLabel.equals("Main_0")) {
            entryLabel = "main";
            exitLabel = "main_exit";
        }

        int frameSize = 4 * body.getVariableCount();

        // Store arguments in a1 to a4 if arg length < 5 else push them onto the stack in reverse order
        if (arguments.size() <= 4) {
            frameSize += 4 * arguments.size();
        }

        String prolog = buildProlog(entryLabel, frameSize);
        String bodyArm = body.generateArm();
        String epilogue = buildEpilogue(exitLabel);

        return String.format("%s" +
                "%s\n" +
                "%s\n",
            prolog, bodyArm, epilogue);
    }


    private String buildProlog(String entryLabel, Integer frameSize) {
        String prolog = String.format("%s:\n" +
                "    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}\n" +
                "    add fp,sp,#24\n"
            , entryLabel);

        if (frameSize > 0) {
            prolog += String.format("    sub sp,fp,#%d\n", frameSize);
        }
        return prolog;
    }

    private String buildEpilogue(String exitLabel) {
        return String.format("%s:\n" +
            "    sub sp,fp,#24\n" +
            "    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}\n", exitLabel);
    }
}
