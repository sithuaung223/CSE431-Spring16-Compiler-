package lab8;
import lab7.AbstractNode;

/** An If node */
public interface IfIsh extends ASTNodeIsh {
  /** The boolean-valued predicate */
  public AbstractNode getPredicate();
  /** The "true" branch */
  public AbstractNode getTruePart();
  /** The "false" branch */
  public AbstractNode getFalsePart();
}
