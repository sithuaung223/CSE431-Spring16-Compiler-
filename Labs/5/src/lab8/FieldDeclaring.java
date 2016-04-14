package lab8;

/**
  * An interface for an AST node that declares an instance or static
  * variable for a class.
  */

public interface FieldDeclaring extends SymDeclaring {
  public ModsAttrs getMods();
  public TypeAttrs getType();
}
