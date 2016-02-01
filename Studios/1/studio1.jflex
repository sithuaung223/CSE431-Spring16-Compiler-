/*
 *  $Id: studio3.jflex 18 2010-05-08 13:33:52Z cytron $
 *
 */

/* This section has code that goes at the top of the generated parser
 *    Things like package declaration and imports
 *    You should need nothing here for this exercise
 */
 
package autogen;
 
%%

/* This section has declarations and options settings
 *
 * First, some handy declarations
 *   Be sure you understand the syntax before moving on
 */
 
%public

LineTerminator = \r | \n | \r\n
InputCharacter = [^\r\n]	
WhiteSpace     = {LineTerminator} | [ \t\f]		/* The blank after the bracket is significant */
Slash = [/]
Minus = [-]
NotSlashMinus = [^/-]

/* Now we tell JFlex we are not part of CUP, just standalone
 */

%standalone

/* Tell JFlex to output the DFA as a table (the default is
 * a packed string, which is harder for humans to read */

%table

/* The following code is emitted in the generated class
 *   You should use it when you find something interesting
 */
 
%{
   /*  Call me to say what you found */
   public void found(String str) {
      System.out.println();  System.out.flush();  /* flush std out */
      System.err.println("Found |" + str + "| from text -->" + yytext() + "<--");
   }
%}

%%

/* Finally, patterns of interest and what to
 *   upon finding them
 */


/*
* ("a" | "b" | "c")+!(\1)	{found("identifier");}
*/
"hello"			{ found("greeting"); }
"goodbye"		{ found("goodbye"); System.exit(0);	 }
"evan" | "ethan" | "SiThu"		{ found("names"); }
"h"("0"|"1")*"1"("0"|"1")("0"|"1") {found("1 2 away");}
"h"("0"|"1")*"1"("0"|"1"){3} {found("3 away");}
"0"*"1""0"*"1"("0"|"1")*		{ found("at least 2 1's");}	
(("0"|"1")*"0")				{ found("even_0"); }
("0"|"1")+		{ found("binary integer"); }