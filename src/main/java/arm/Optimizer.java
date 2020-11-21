package main.java.arm;

public class Optimizer {
    public static String optimize(String original) {
        String temp = original;

        // Refer to report for explanation and proof!

        // Optimization 1
        temp = temp.replaceAll("str (\\w\\w), (.*)(\\s+)ldr \\1, \\2", "str $1, $2");

        // Optimization 2
        temp = temp.replaceAll("str (\\w\\w), (.*)(\\s+)ldr (\\w\\w), \\2", "str $1, $2$3mov $4, $1");

        // keep running until converges
        return original.equals(temp) ? temp : optimize(temp);
    }
}