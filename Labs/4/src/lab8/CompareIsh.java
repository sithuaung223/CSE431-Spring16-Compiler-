package lab8;
import lab7.AbstractNode;

/** Represents all of the comparison operators. */
public interface CompareIsh extends ASTNodeIsh {
   /** Return the actual operator. Could be one of:
     <UL>  <LI> lt
           <LI> gt
           <LI> ge
           <LI> le
           <LI> ne
           <LI> eq
     </UL>
   */
   public String getCompare();
}
