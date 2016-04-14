package lab8;
import lab7.AbstractNode;

/** A node whose right-child type must agree with the left-child type,
  * as with an assignment or a cast node */

public interface AssignTypeRequiring { 
   /** Get the left child (target type) */
   public AbstractNode getAssignTypeNode();
   /** Get the right child */
   public AbstractNode getSubjectNode();
}
