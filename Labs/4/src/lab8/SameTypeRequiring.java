package lab8;
import lab7.AbstractNode;

/** A node whose semantics want the left and rightchild to agree on type.  For extra
  * credit you can handle promotions to make this work.
  */
public interface SameTypeRequiring { 
   public AbstractNode getLeftChild();
   public AbstractNode getRightChild();
}
