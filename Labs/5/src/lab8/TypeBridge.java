package lab8;

/** This class bridges the generic Type interface from the parser to actual types
  * used in semantic analysis.  Because this class implements TypeAttrs, it is
  * also a hw3.Type.
  */
abstract public class TypeBridge implements TypeAttrs {
   abstract public boolean isPrimitive();
   /** A type string suitable for the JVM. */
   abstract public String getTypeString();
   public String toString() { return getTypeString(); }
}

