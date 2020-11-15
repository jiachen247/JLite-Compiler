/*
  This example comes from a short article series in the Linux
  Gazette by Richard A. Sevenich and Christopher Lopes, titled
  "Compiler Construction Tools". The article series starts at

  http://www.linuxgazette.com/issue39/sevenich.html

  Small changes and updates to newest JFlex+Cup versions
  by Gerwin Klein
*/

/*
  Commented By: Christopher Lopes

  <p>To Run:
  <pre>
  java Main test.txt
  </pre>
  where {@code test.txt} is an test input file for the calculator.
*/

import java.io.FileReader;

import main.java.ir3.Program3;
import main.java.parsetree.Program;
import main.java.staticcheckers.Checker;
import main.java.staticcheckers.NameChecker;
import main.java.staticcheckers.TypeChecker;


public class Main {
    public static void main(String[] argv) {
        boolean debug = false;
        boolean optimize = false;

        for (String option : argv) {
            if (option.equals("-o") || option.equals("--optimize")) {
                optimize = true;
            } else if (option.equals("-d") || option.equals("--debug")) {
                debug = true;
            }
        }


        /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            Program program = (Program) p.parse().value;

            if (debug) {
                System.out.println("=== Successfully parsed program! ===");
                System.out.println(program);
            }

            // assign unique method numbers to each class method
            program.assignMethodNumbers();

            Checker nameChecker = new NameChecker(program);

            if (!nameChecker.isOK()) {
                nameChecker.printErrors();
                return;
            }
            if (debug) {
                System.out.println("DistinctNameCheck:\tPASSED");
            }

            Checker typeChecker = new TypeChecker(program);

            if (!typeChecker.isOK()) {
                typeChecker.printErrors();
                return;
            }
            if (debug) {
                System.out.println("TypeChecked:\t\tPASSED");
            }
            // proceed to gen IR

            Program3 ir = program.toProgram3();

            if (debug) {
                System.out.println(ir);
            }
            if (debug) {
                System.out.println("==== Start of generated ARM code ====");
            }
            String assembly = ir.generateArm(optimize);
            System.out.println(assembly);

            if (debug) {
                System.out.println("==== End of generated ARM code ====");
            }

        } catch (Exception e) {
            /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}
