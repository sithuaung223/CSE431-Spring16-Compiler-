package lab8;

/** A node that produces an ldc-type constant value */
public interface ConstantProducing extends ASTNodeIsh {
   public String getConstant();
}
