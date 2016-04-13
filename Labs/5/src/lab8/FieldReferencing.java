package lab8;
import lab7.Type;

public interface FieldReferencing extends Referencing {
   /** The type of the resulting reference */
   public Type getResultingType();
   /** The last part of the qualified name (the component) */
   public String  getFieldName();
}
