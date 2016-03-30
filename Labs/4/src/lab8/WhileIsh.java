package lab8;
import lab7.AbstractNode;

/** A while node */
public interface WhileIsh extends ASTNodeIsh {
   public AbstractNode getPredicate();
   public AbstractNode getBody();
}
