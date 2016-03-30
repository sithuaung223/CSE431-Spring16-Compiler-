package lab8;
import lab7.AbstractNode;

/** Everything you want to know about a symbol */

public interface SymInfo {
   /** This shows us the node that declares the symbol */
   public AbstractNode getDefiningNode();
   public TypeAttrs getType();
   public ModsAttrs getMods();
   public int getRegister();
   public void setRegister(int reg);
}
