package lab7;
import java.io.PrintStream;

/** Obsolete do not use */
public class PrintTree implements Visitable {
    PrintStream ps;
    public PrintTree(PrintStream ps) {
       this.ps = ps;
   }
    public void pre(int level,  AbstractNode n) {
       String tab = "";
       for (int i=1; i <= level; ++i) tab += "  ";
       ps.println(tab + n.toString());
    }
    public void post(int i, AbstractNode a) {}
}
