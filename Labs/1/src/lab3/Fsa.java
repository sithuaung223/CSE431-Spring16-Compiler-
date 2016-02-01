/**
 * CSE 431 Lab 1
 * 
 * Evan Schwartzman
 * Si Thu Aung
 * 
 * We treated special tokens such as terminal as regular strings and had them mimic that behavior in many instances
 * Our thinking was that, if such a keyword was meant to be a string, and not the keyword, it should be accepted, and that if it was
 * meant to be the actual keyword, it would inevitably fail later on, so it was okay to do this
 * After the DFA reads in the Starts with part, it enters a sub loop designed only for reading productions
 * 
 * It passes all test files, except the bad one is not thread safe, but still has correct stream output
 */

package lab3;

import java.util.Enumeration;

public class Fsa {

 static int GOTO[][] = {
/*     B    D    S    O    C    S    T    N    S    W    O */
/*     l    e    e    r    o    t    e    o    t    i    t */
/*     a    f    m         m    r    r    n    a    t    h */
/*     n    i    i         m         m         r    h    e */
/*     k    n              a         i         t         r */
/*          e                        n                     */
/*                                   a                     */
/*                                   l                     */
/*                                                         */
{      0,  17,  17,  17,  17,  17,   1,   2,   3,  17,  17} /* 0 */,
{      1,  17,  17,  17,  17,   5,   5,   5,   5,  5,   17} /* 1 */,
{      2,  17,  17,  17,  17,  17,  13,  17,  17,  17,  17} /* 2 */,
{      3,  17,  17,  17,  17,  17,  17,  17,  17,   8,  17} /* 3 */,
{      4,  10,  17,  17,  17,  17,  17,  17,  17,  17,  17} /* 4 */,  
{      5,  17,  17,  17,  17,   6,   6,   6,   6,   6,  17} /* 5 */,
{      6,  17,   7,  17,   5,  17,  17,  17,  17,  17,  17} /* 6 */,
{      0,  17,  17,  17,  17,  17,  17,  17,  17,  17,  17} /* 7 */,
{      8,  17,  17,  17,  17,   9,   9,   9,   9,   9,  17} /* 8 */,
{      9,  17,  16,  17,  17,  17,  17,  17,  17,  17,  17} /* 9 */,
{      10, 17,  17,  17,  17,  11,  11,  11,  11,  11,  17} /* 10 */,
{      11, 17,  16,  10,  17,  12,  12,  12,  12,  12,  17} /* 11 */,
{      12, 17,  16,  10,  17,  17,  17,  17,  17,  17,  17} /* 12 */,
{      13, 17,  17,  17,  17,  14,  14,  14,  14,  14,  17} /* 13 */,
{      14, 17,  17,  17,  17,  15,  15,  15,  15,  15,  17} /* 14 */,
{      15, 17,   7,  17,  14,  17,  17,  17,  17,  17,  17} /* 15 */,
{      16, 17,  17,  17,  17,   4,   4,   4,   4,   4,  17} /* 16 */,
};

 static int ACTION[][] = {
/*     B    D    S    O    C    S    T    N    S    W    O */
/*     l    e    e    r    o    t    e    o    t    i    t */
/*     a    f    m         m    r    r    n    a    t    h */
/*     n    i    i         m         m         r    h    e */
/*     k    n              a         i         t         r */
/*          e                        n                     */
/*                                   a                     */
/*                                   l                     */
/*                                                         */
{      1,   2,   2,   2,   2,   2,   1,   1,   1,   2,   2} /* 0 */,
{      1,   2,   2,   2,   2,   1,   1,   1,   1,   1,   2} /* 1 */,
{      1,   2,   2,   2,   2,   2,   1,   2,   2,   2,   2} /* 2 */,
{      1,   2,   2,   2,   2,   2,   2,   2,   2,   1,   2} /* 3 */,
{      1,   1,   2,   2,   2,   2,   2,   2,   2,   2,   2} /* 4 */,
{      1,   2,   2,   2,   2,   3,   3,   3,   3,   3,   2} /* 5 */,
{      1,   2,   1,   2,   1,   2,   2,   2,   2,   2,   2} /* 6 */,
{      1,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2} /* 7 */,
{      1,   2,   2,   2,   2,   9,   9,   9,   9,   9,   2} /* 8 */,
{      1,   2,   1,   2,   2,   2,   2,   2,   2,   2,   2} /* 9 */,
{      1,   2,   2,   2,   2,   6,   6,   6,   6,   6,   2} /* 10 */,
{      1,   2,   8,   8,   2,   7,   7,   7,   7,   7,   2} /* 11 */,
{      1,   2,   1,   1,   2,   2,   2,   2,   2,   2,   2} /* 12 */,
{      1,   2,   2,   2,   2,   1,   1,   1,   1,   1,   2} /* 13 */,
{      1,   2,   2,   2,   2,   4,   4,   4,   4,   4,   2} /* 14 */,
{      1,   2,   1,   2,   1,   2,   2,   2,   2,   2,   2} /* 15 */,
{      1,   2,   2,   2,   2,   5,   5,   5,   5,   5,   2} /* 16 */,
};

   public Fsa(Enumeration e) {
      SymbolTable symboltable = new SymbolTable();

      int state = 0;
      String 
         lhs     = "", 
         term    = "", 
         nonterm = "$FINAL$";
      symboltable.enterNonTerminal("$FINAL$");

      while (e.hasMoreElements()) {
         Token t = (Token)e.nextElement();

         int action = ACTION[state][t.type()];
         int newstate = GOTO[state][t.type()];

         switch (action) {
            case  1: /* do nothing 	*/
                     break;
            case  2: /* fail 		*/
            	oops(t.strValue());
            case  3: /* mark value terminal	*/ 
            	symboltable.enterTerminal(t.strValue());
            	break;
            case  4: /* mark value non terminal */
            	symboltable.enterNonTerminal(t.strValue());
            	break;
            case  5: /* set left hand edge */
            	if (symboltable.isTerminal(t.strValue()) == false ){
            		lhs = t.strValue();
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops("Expected non terminal:" + " " + t.strValue());
            	}
            case  6: /* set terminal value */
            	if (symboltable.isTerminal(t.strValue()) == false ){
            		oops("Expected terminal:" + " " + t.strValue());
            	}else if (symboltable.isTerminal(t.strValue())){
            		term = t.strValue();
            		break;
            	}
            case  7: /* set non terminal value and print out string */
            	if (symboltable.isTerminal(t.strValue()) == false ){
            		nonterm = t.strValue();
            		System.out.println("Edge" + " "+lhs + " " + nonterm + " " + term );
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops("Expected non terminal:" + " " + t.strValue());
            	}
            case  8: /* set non terminal value and print out string if we are in final stage */
            	if (symboltable.isTerminal(nonterm) == false ){
            		nonterm = "$FINAL$";
            		System.out.println("Edge" + " "+lhs + " " + nonterm + " " + term );
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops(t.strValue()); 
            	}
            case  9: /* print out the term that follows 'start with' */
            	System.out.println("Start" + " "+t.strValue());
            	break;
         }
         state = newstate;
      }
      if (state != 16) oops("End in bad state: " + state); //fail if it does not end in the accepting state (state 16)
   }

   void oops(String s) {
      System.err.println("Error: " + s);
      System.out.println("ABORT");
      System.exit(-1);
   }
}
