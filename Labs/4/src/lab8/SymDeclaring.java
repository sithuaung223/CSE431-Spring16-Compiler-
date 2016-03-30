package lab8;

/** A convenient interface for anything that declares a symbol. */
public interface SymDeclaring extends ASTNodeIsh {
   public String getName();
   public SymInfo getSymInfo();
   public void setSymInfo(SymInfo s);
}
