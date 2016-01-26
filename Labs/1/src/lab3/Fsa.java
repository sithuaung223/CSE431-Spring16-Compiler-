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
{      0,   0,   0,   0,   0,   0,   0,   0,   0,   0,   0} /* 0 */,
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
{      1,   1,   1,   1,   1,   1,   1,   1,   1,   1,   1} /* 0 */,
};

   public Fsa(Enumeration e) {
      // Uncomment the line below and each token processed will be echoed.
      // ((TokenEnum)e).setDebug(true);

      SymbolTable symboltable = new SymbolTable();

      int state = 0;

      String 
         lhs     = "", 
         term    = "", 
         nonterm = "$FINAL$";

      while (e.hasMoreElements()) {
         Token t = (Token)e.nextElement();

         System.out.println("   Read token type " + t.type() + ": " + t);

         int action = ACTION[state][t.type()];
         int newstate = GOTO[state][t.type()];

         System.out.println("State " + state +
                " Performing action " + action + " and going to " + newstate);

         switch (action) {
            case  1: /* do nothing */
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
