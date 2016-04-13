package lab8;

/** A node that produces a constant String value */
public interface ConstantString extends StringTypeProducing {
   /** The String value of this node */
   public String getVal();
}
