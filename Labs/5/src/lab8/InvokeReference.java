package lab8;
import lab7.AbstractNode;
import lab7.Type;

/** A reference to an invocation */
public interface InvokeReference extends ASTNodeIsh {
   public Type         getClassType();
   public String       getMethodName();
   public Type         getReturnType();
   public boolean      isStaticInvoke();
   /** returns null if this is a static reference */
   public AbstractNode getThisNode();
}
