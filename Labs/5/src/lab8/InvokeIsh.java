package lab8;
import java.util.Enumeration;

import lab7.AbstractNode;

/** Represents a method call. */
public interface InvokeIsh extends ASTNodeIsh {
   public Enumeration params();
   public InvokeReference methodNode();
   public AbstractNode paramsNode();
}
