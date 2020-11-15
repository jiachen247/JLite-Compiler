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
        this.offsetTable = new HashMap<>();
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

    private String buildStack(Integer frameSize) {
        // stack grows toward lower address

        StringBuilder sb = new StringBuilder();

        // MIGHT BE 28 instead
        int offset  = 24; // for fp, lr and v0-5
        /*
            If len(args) <= 4
               fp -> | fp, lr, v0-v5 | arg n to 1 | local variables | temps | <- sp

           If len(args) > 4
                args n to 1 | fp -> fp, lr, v0-v5 | local variables | temps | <- sp

         */

        if (arguments.size() <= 4) {
            int regInd = 1; // a1 to a4
            for (Argument arg : arguments) {
                sb.append(String.format("    str a%d, [fp, #%d]\n", regInd, offset));
                offsetTable.put(arg.id.name, offset);
                regInd += 1;
                offset += 4;
            }
        } else {
            // args already on the stack below bp
            int argPtr = 0;
            for (int i = arguments.size() - 1; i >= 0; i--) {
                offsetTable.put(arguments.get(i).id.name, argPtr);
                argPtr -= 4;
            }
        }

        for (VarDecl3 decl : body.getVariableDeclarations()) {
            offsetTable.put(decl.getId().getName(), offset);
            offset += 4;
        }

        return sb.toString();
    }


    private String buildProlog(String entryLabel, Integer frameSize) {
        String prolog = String.format("%s:\n" +
                "    stmfd sp!,{fp,lr,v1,v2,v3,v4,v5}\n" +
                "    add fp,sp,#24\n"
            , entryLabel);

        if (frameSize > 0) {
            prolog += String.format("    sub sp,fp,#%d\n", frameSize);
            prolog += buildStack(frameSize);
        }

        return prolog;
    }

    private String buildEpilogue(String exitLabel) {
        return String.format("%s:\n" +
            "    sub sp,fp,#24\n" +
            "    ldmfd sp!,{fp,pc,v1,v2,v3,v4,v5}\n", exitLabel);
    }
}
