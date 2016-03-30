package lab8;
import lab7.Type;

/** A node that asserts a reference type. Such nodes are found under CAST and under
  * field references */
public interface TypeAsserting {
   public Type getType();
}
