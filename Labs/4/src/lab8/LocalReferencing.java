package lab8;

/** Reference to a local variable */
public interface LocalReferencing extends Referencing {
   /** A hook to the appropriate declaration.  Your symbol table visitor should call
     * this method after looking up the id.
     */
   public String getId();
   public void setSymInfo(SymInfo syminfo);
   public SymInfo getSymInfo();
}
