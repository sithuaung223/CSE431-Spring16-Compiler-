package lab8;
import lab7.AbstractNode;
import lab7.ReflectiveVisitor;

/**
  * This interface allows you to use my symbol-table solution or yours
  * interchangeably
  */

public interface SymtabInterface {
   /** Open a new nest */
   public void incrNestLevel();
   /** Close an old nest */
   public void decrNestLevel();
   public int getCurrentNestLevel();
   public SymInfo lookup(String id);
   public void enter(String id, SymInfo s);
   /** This lets you put out a message about a node, indented by the current nest level */
   public void out(AbstractNode n, String message);
   public void err(AbstractNode n, String message);
   public void out(String message);
   public void err(String message);
}

