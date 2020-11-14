package main.java.ir3;

import java.util.List;

import com.sun.nio.sctp.NotificationHandler;
import main.java.arm.StringLabels;

public class Program3 {
    private List<CData3> classes;
    private List<CMtd3> methods;

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

    public String generateArm(boolean toOptimize) {
        // StringLabels.getInstance().getLabel("blahh");

        StringBuilder body = new StringBuilder();

        for (CMtd3 method : methods) {
            body.append(method.generateArm(toOptimize, classes));
        }

        return String.format(".data\n" +
                "%s\n" +
                ".text\n" +
                ".global main\n" +
                ".type main, %%function\n\n" +
                "%s\n\n" +
                ".end\n",
            StringLabels.getInstance().toString(),
            body.toString());
    }
}
