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
{      0,  16,  16,  16,  16,   4,   1,   2,   3,  16,  16} /* 0 */,
{      1,  16,  16,  16,  16,   5,  16,  16,  16,  16,  16} /* 1 */,
{      2,  16,  16,  16,  16,  16,  13,  16,  16,  16,  16} /* 2 */,
{      3,  16,  16,  16,  16,  16,  16,  16,  16,   8,  16} /* 3 */,
{      4,  10,  16,  16,  16,  16,  16,  16,  16,  16,  16} /* 4 */,  
{      5,  16,  16,  16,  16,   6,  16,  16,  16,  16,  16} /* 5 */,
{      6,  16,   7,  16,   5,  16,  16,  16,  16,  16,  16} /* 6 */,
{      0,  16,  16,  16,  16,  16,  16,  16,  16,  16,  16} /* 7 */,
{      8,  16,  16,  16,  16,   9,  16,  16,  16,  16,  16} /* 8 */,
{      9,  16,   7,  16,  16,  16,  16,  16,  16,  16,  16} /* 9 */,
{      10, 16,  16,  16,  16,  11,  16,  16,  16,  16,  16} /* 10 */,
{      11, 16,   7,  10,  16,  12,  16,  16,  16,  16,  16} /* 11 */,
{      12, 16,   7,  10,  16,  16,  16,  16,  16,  16,  16} /* 12 */,
{      13, 16,  16,  16,  16,  14,  16,  16,  16,  16,  16} /* 13 */,
{      14, 16,  16,  16,  16,  15,  16,  16,  16,  16,  16} /* 14 */,
{      15, 16,   7,  16,  14,  16,  16,  16,  16,  16,  16} /* 15 */,
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
{      1,   2,   2,   2,   2,   5,   1,   1,   1,   2,   2} /* 0 */,
{      1,   2,   2,   2,   2,   1,   2,   2,   2,   2,   2} /* 1 */,
{      1,   2,   2,   2,   2,   2,   1,   2,   2,   2,   2} /* 2 */,
{      1,   2,   2,   2,   2,   2,   2,   2,   2,   1,   2} /* 3 */,
{      1,   1,   2,   2,   2,   2,   2,   2,   2,   2,   2} /* 4 */,
{      1,   2,   2,   2,   2,   3,   2,   2,   2,   2,   2} /* 5 */,
{      1,   2,   1,   2,   1,   2,   2,   2,   2,   2,   2} /* 6 */,
{      1,   2,   2,   2,   2,   2,   2,   2,   2,   2,   2} /* 7 */,
{      1,   2,   2,   2,   2,   9,   2,   2,   2,   2,   2} /* 8 */,
{      1,   2,   1,   2,   2,   2,   2,   2,   2,   2,   2} /* 9 */,
{      1,   2,   2,   2,   2,   6,   2,   2,   2,   2,   2} /* 10 */,
{      1,   2,   8,   8,   2,   7,   2,   2,   2,   2,   2} /* 11 */,
{      1,   2,   1,   1,   2,   2,   2,   2,   2,   2,   2} /* 12 */,
{      1,   2,   2,   2,   2,   1,   2,   2,   2,   2,   2} /* 13 */,
{      1,   2,   2,   2,   2,   4,   2,   2,   2,   2,   2} /* 14 */,
{      1,   2,   1,   2,   1,   2,   2,   2,   2,   2,   2} /* 15 */,
};

   public Fsa(Enumeration e) {
      // Uncomment the line below and each token processed will be echoed.
       //((TokenEnum)e).setDebug(true);

      SymbolTable symboltable = new SymbolTable();

      int state = 0;
      boolean counter = false;
      String 
         lhs     = "", 
         term    = "", 
         nonterm = "$FINAL$";
      symboltable.enterNonTerminal("$FINAL$");

      while (e.hasMoreElements()) {
         Token t = (Token)e.nextElement();

         //System.out.println("   Read token type " + t.type() + ": " + t);

         int action = ACTION[state][t.type()];
         int newstate = GOTO[state][t.type()];

         //System.out.println("State " + state +
           //     " Performing action " + action + " and going to " + newstate);

         switch (action) {
            case  1: /* do nothing */
                     break;
            case  2:
            	oops(t.strValue());
            case  3:
            	symboltable.enterTerminal(t.strValue());
            	break;
            case  4:
            	symboltable.enterNonTerminal(t.strValue());
            	break;
            case  5:
            	if (symboltable.isTerminal(t.strValue()) == false){
            		lhs = t.strValue();
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops(t.strValue());
            	}
            case  6:
            	if (symboltable.isTerminal(t.strValue()) == false){
            		oops(t.strValue());
            	}else if (symboltable.isTerminal(t.strValue())){
            		term = t.strValue();
            		break;
            	}
            case  7:
            	if (symboltable.isTerminal(t.strValue()) == false){
            		nonterm = t.strValue();
            		System.out.println("Edge" + " "+lhs + " " + nonterm + " " + term );
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops(t.strValue());
            	}
            case  8:
            	if (symboltable.isTerminal(nonterm) == false){
            		nonterm = "$FINAL$";
            		System.out.println("Edge" + " "+lhs + " " + nonterm + " " + term );
            		break;
            	}else if (symboltable.isTerminal(t.strValue())){
            		oops(t.strValue()); 
            	}
            case  9:
            	System.out.println("Start" + " "+t.strValue());
            	break;
         }
         state = newstate;
      }
      if (state != 0) oops("End in bad state: " + state);
   }

   void oops(String s) {
      System.err.println("Error: " + s);
      System.out.println("ABORT");
      System.exit(-1);
   }
}
