package lab8;

import lab7.*;

/** Abstract class so you can print out messages that are properly indented to
  * reflect the current nest level.
  */

abstract public class Symtab {


   public abstract int getCurrentNestLevel();

   public void out(String s) {
         String tab = "";
         for (int i=1; i <= getCurrentNestLevel(); ++i) tab += "  ";
         System.out.println(tab + s);
   }

   public void err(String s) {
      out("Error: " + s);
      System.err.println("Error: " + s);
      System.exit(-1);
   }

   public void out(AbstractNode n, String s) {
        out(""+n.getNodeNum()+": " + s + " " + n);
   }
   public void err(AbstractNode n, String s) {
        err(""+n.getNodeNum()+": " + s + " " + n);
   }

}

