package lab3;

import lab3.ReflectiveVisitor;

/** Reflective visitor pattern -- a node must accept a visitor */
public interface ReflectiveVisitable {
   public void accept(ReflectiveVisitor rv);
}
