package lab8;
import lab7.AbstractNode;

/** Reference to a static field */
public interface StaticReferencing extends FieldReferencing {
   /** All but the last part of the qualified name */
   public AbstractNode  getClassName();
}
