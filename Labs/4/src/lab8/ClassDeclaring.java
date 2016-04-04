package lab8;

/**
  * An interface for an AST node that declares a class
  * Classes have modifiers, like public/private, but no type
  */

public interface ClassDeclaring extends SymDeclaring {
  public ModsAttrs getMods();
}
