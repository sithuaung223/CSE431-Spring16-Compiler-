package lab8;
import lab7.AbstractNode;

public interface ClassIsh extends ASTNodeIsh {
   public AbstractNode getConstructors();
   public AbstractNode getMethods();
   public AbstractNode getFields();
   public AbstractNode getStatics();
   public AbstractNode getInners();
}
