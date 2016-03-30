package lab8;
import lab7.AbstractNode;

/** This interface should be implemented by any AST node that represents a block {.....}.
  * You should incrmenet nest level before processing the children and decrement on the
  * way back.  The children of a BlockIsh node are obvious, so no method is needed to
  * get them -- they are simply the children of the AST node.
  */
public interface BlockIsh extends ASTNodeIsh {
}
