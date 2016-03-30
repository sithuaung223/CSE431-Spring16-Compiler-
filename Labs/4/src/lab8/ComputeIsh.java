package lab8;
import lab7.AbstractNode;

/** Represent an arithmetic node--generate code for its children then put out its operation.
You have to prepend the type code, like "i", to the operation  */
public interface ComputeIsh extends ASTNodeIsh {
   /** Could be one of add, sub, mul, div, rem */
   public String getOperation();
}
