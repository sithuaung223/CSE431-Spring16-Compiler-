package lab8;
import lab7.*;


public interface HierarchyInterface {

   /** Type a is the parent type of b */
   public void assertParentOf(Type a, Type b);

   /** No more changes.  Make sure the tree is rooted and compute depth-first stuff */
   public void finished();

   /** is t1 a direct or indirect subclass of t2? */
   public boolean narrows(Type t1, Type t2);

   /** Is t1 a direct or indirect superclass of t2? */
   public boolean widens(Type t1, Type t2);

   /** Return the parent of the supplied type, null if none */
   public Type getSuperClass(Type t1);
}
