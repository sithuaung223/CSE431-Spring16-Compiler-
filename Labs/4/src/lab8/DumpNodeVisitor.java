package lab8;
import java.io.PrintStream;

import lab7.*;

/** This visitor shows you the AST as-is, node for node, with node types
  * printed out.  The number printed is useful as it can help correlate the declaration
  * node for a symbol with references of that symbol.
  */

   public class DumpNodeVisitor extends NodeVisitor {
      public DumpNodeVisitor(PrintStream ps) { super(); setPrintStream(ps); }

      public void defaultVisit(Object o) {
         AbstractNode n = (AbstractNode) o;
         // Type t = n.getNodeType();
         // String tString = (t != null) ? ("<"+t.toString()+"> ") : "";
         // out("" + n.getNodeNum() + ": " + tString + n.whatAmI() + 
         //    "  \"" + n.toString()+"\"");
         out(n.dump());
         visitChildren(n);
      }


   }
