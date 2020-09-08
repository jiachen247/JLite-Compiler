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
  File Name: lcalc.flex
  To Create: > jflex lcalc.flex

  and then after the parser is created
  > javac Lexer.java
*/
   
/* --------------------------Usercode Section------------------------ */
   
import java_cup.runtime.*;
      
%%
   
/* -----------------Options and Declarations Section----------------- */
   
/* 
   The name of the class JFlex will create will be Lexer.
   Will write the code to the file Lexer.java. 
*/
%class Lexer

/*
  The current line number can be accessed with the variable yyline
  and the current column number with the variable yycolumn.
*/
%line
%column
    
/* 
   Will switch to a CUP compatibility mode to interface with a CUP
   generated parser.
*/
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{
    private StringBuilder sb;
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
line_terminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or line feed. */
whitespace     = {line_terminator} | [ \t\f]
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
dec_int_lit = 0 | [1-9][0-9]*
   
/* A id is a word beginning a letter between a and
   z followed by zero or more letters between A and Z, a and z,
   zero and nine, or an underscore. */
id = [a-z][A-Za-z_0-9]*


/* A class name (cname) is a word beginning a letter between A and
   Z followed by zero or more letters between A and Z, a and z,
   zero and nine, or an underscore. */
cname = [A-Z][A-Za-z_0-9]*

%state SINGLE_COMMENT
%state BLOCK_COMMENT
%state STRING_LITERAL
   
%%
/* ------------------------Lexical Rules Section---------------------- */
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
   /* YYINITIAL is the state at which the lexer begins scanning.  So
   these regular expressions will only be matched if the scanner is in
   the start state YYINITIAL. */
   
<SINGLE_COMMENT> {
    {line_terminator} { yybegin(YYINITIAL); }
    
    . { }
}

<BLOCK_COMMENT> {
    \*\/ { yybegin(YYINITIAL); }
    
    . { }
    
    <<EOF>> { throw new Error("Unexpected eof. multi line comment not terminated."); }
}

<STRING_LITERAL> {
// todo change this
// we just append any contiguous string of non \ \n \t \r \b " characters to the string
[^\\\n\t\r\b\"]+ { sb.append(yytext()); }

{line_terminator} { throw new Error("Unexpected new line within a string"); }

// special characters
\\\\ { sb.append('\\'); }
\\n { sb.append('\n'); }
\\t { sb.append('\t'); }
\\r { sb.append('\r'); }
\\b { sb.append('\b'); }
\\\" { sb.append('"'); }

// escaped ascii hex code
\\x[a-fA-F0-9][a-fA-F0-9] {
  int value = Integer.parseInt(yytext().substring(2), 16);
//  if (value > MAX_ASCII_CHAR) {
//    error(yytext() + " is not a valid ascii character in hex");
//  }
  sb.append((char) value);
}

// escaped decimal character
\\[0-9][0-9][0-9] {
  int value = Integer.parseInt(yytext().substring(1), 10);
//  if (value > MAX_ASCII_CHAR) {
//    error(yytext() + " is not a valid ascii character in decimal");
//  }
  sb.append((char) value);
}

// allow any other character after \ to return itself (redundant escape)
\\. { sb.append(yytext().substring(1)); }

// end of string, build the string then return to YYINITIAL state
\" { yybegin(YYINITIAL); return symbol(sym.STRING_LITERAL, sb.toString());}

<<EOF>> { throw new Error("Unexpected EOF, string not yet terminated."); }
}

   
<YYINITIAL> {
    /* Return the token SEMI declared in the class sym that was found. */
    ";"  { return symbol(sym.SEMI); }
    ","  { return symbol(sym.COMMA); }

    /* Print the token found that was declared in the class sym and then
        return it. */
    "+" { System.out.print(" + "); return symbol(sym.PLUS); }
    "-" { System.out.print(" - "); return symbol(sym.MINUS); }
    "*" { System.out.print(" * "); return symbol(sym.TIMES); }
    "/" { System.out.print(" / "); return symbol(sym.DIVIDE); }
    "(" { System.out.print(" ( "); return symbol(sym.LPAREN); }
    ")" { System.out.print(" ) "); return symbol(sym.RPAREN); }
    "{" { System.out.print(" { "); return symbol(sym.LBRACE); }
    "}" { System.out.print(" } ");return symbol(sym.RBRACE); }


    // comparision operators
    "<" { System.out.print(" < "); return symbol(sym.GT); }
    ">" { System.out.print(" > "); return symbol(sym.LT); }
    ">=" { System.out.print(" >= "); return symbol(sym.GEQ); }
    "<=" { System.out.print(" <= "); return symbol(sym.LEQ); }
    "==" { System.out.print(" == "); return symbol(sym.EQ); }
    "!=" { System.out.print(" != "); return symbol(sym.NEQ); }

    // logical operators
    "&&" { return symbol(sym.AND); }
    "||" { return symbol(sym.OR); }

    // misc
    "=" { System.out.print(" = "); return symbol(sym.ASSIGN); }
    "!" { System.out.print(" ! "); return symbol(sym.NOT); }
    "." { System.out.print(" . "); return symbol(sym.IN); }

    // boolean literals
    "true" { System.out.print(" true "); return symbol(sym.TRUE); }
    "false" { System.out.print(" false "); return symbol(sym.FALSE); }

    // Base types
    "Int" { System.out.print(" Int "); return symbol(sym.INT);}
    "Bool" { System.out.print(" Bool "); return symbol(sym.BOOL);}
    "String" { System.out.print(" String "); return symbol(sym.STRING);}
    "Void" { System.out.print(" Void "); return symbol(sym.VOID);}


    // Jlite keywords
    "class" { System.out.print(" class "); return symbol(sym.CLASS);}
    "if" { System.out.print(" if "); return symbol(sym.IF);}
    "else" { System.out.print(" else "); return symbol(sym.ELSE);}
    "while" { System.out.print(" while "); return symbol(sym.WHILE);}
    "readln" { System.out.print(" readln "); return symbol(sym.READLN);}
    "println" {System.out.print(" println "); return symbol(sym.PRINTLN);}
    "return" { System.out.print(" return "); return symbol(sym.RETURN);}
    "main" { System.out.print(" main "); return symbol(sym.MAIN);}
    "this" { System.out.print(" this "); return symbol(sym.THIS);}
    "new" { System.out.print(" new "); return symbol(sym.NEW);}
    "null" { System.out.print(" null "); return symbol(sym.NULL);}


    /* Don't do anything if whitespace is found */
    {whitespace}       { /* just skip what was found, do nothing */ }

    /* add comments */
    \/\/ { yybegin(SINGLE_COMMENT); }
    \/\* { yybegin(BLOCK_COMMENT); }


    \" { sb = new StringBuilder(); yybegin(STRING_LITERAL); }



    /* If an integer is found print it out, return the token NUMBER
       that represents an integer and the value of the integer that is
       held in the string yytext which will get turned into an integer
       before returning */
    {dec_int_lit}   { System.out.print(yytext()); return symbol(sym.INTEGER_LITERAL, Integer.valueOf(yytext())); }
   
    /* If an id is found print it out, return the token ID
       that represents an id and the default value one that is
       given to all identifiers. */
    {id}  { System.out.print(yytext()); return symbol(sym.ID, yytext());}

    {cname} { return symbol(sym.CNAME, (yytext())); }

}


/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]  { throw new Error("Illegal character <"+yytext()+">"); }
