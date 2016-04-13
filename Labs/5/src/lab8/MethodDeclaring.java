package lab8;
import lab7.AbstractNode;

/**
  * An interface for an AST node that declares a method.  This allows you to use my jmm.cup
  * or your own.  For your own, make sure that if a node represents a method declaration, then
  * it implements this interface.
  */

public interface MethodDeclaring extends SymDeclaring {
  public ModsAttrs getMods();
  public TypeAttrs getType();
  public AbstractNode getParams();
  public AbstractNode getBody();
}
