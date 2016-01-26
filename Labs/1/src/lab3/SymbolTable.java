package lab3;

import java.util.Hashtable;

public class SymbolTable {
   private static Object Terminal, NonTerminal;
   private Hashtable<String,Object> ht;

   static {
      Terminal = new Object();
      NonTerminal = new Object();
   }

   public SymbolTable() {
      ht = new Hashtable<String,Object>();
   }

   public final boolean inTable(String s) {
      return(ht.get(s.trim()) != null);
   }

   public boolean isTerminal(String s) {
      s = s.trim();
      if (inTable(s)) {
         return(ht.get(s) == Terminal);
      }
      else throw new SymbolNotFoundError(
           "Symbol \"" + s + "\" not previous entered in table"
                                            );
   }

   public void enterTerminal(String s) {
      ht.put(s.trim(), Terminal);
   }

   public void enterNonTerminal(String s) {
      ht.put(s.trim(), NonTerminal);
   }

   public static void main(String[] args) { // just for checking
      String t = "I am a terminal";
      String nt = "I am a nonterminal";
      SymbolTable st = new SymbolTable();
      st.enterNonTerminal(nt);
      st.enterTerminal(t);
      System.out.println("Nonterminal: " + st.isTerminal(nt+" "));
      System.out.println("Terminal: " + st.isTerminal(t+""));
      System.out.println("Undeclared: " + st.isTerminal("missing"));
   }
}
