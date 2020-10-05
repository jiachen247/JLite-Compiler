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

import main.java.parsetree.Program;
import main.java.staticcheckers.Checker;
import main.java.staticcheckers.NameChecker;
import main.java.staticcheckers.TypeChecker;


public class Main {
    public static void main(String[] argv) {
        /* Start the parser */
        try {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            Program program = (Program) p.parse().value;


            System.out.println(program);

            Checker nameChecker = new NameChecker(program);

            if (!nameChecker.isOK()) {
                System.out.println("[1/2] DistinctNameChecker: FAIL");
                return;
            }

            System.out.println("[1/2] DistinctNameChecker: PASS");

            Checker typeChecker = new TypeChecker(program);

            if (!typeChecker.isOK()) {
                System.out.println("[2/2] TypeChecker: FAIL");
                return;
            }
            System.out.println("[2/2] TypeChecker: PASS");
            // proceed to gen IR

        } catch (Exception e) {
            /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }
}
