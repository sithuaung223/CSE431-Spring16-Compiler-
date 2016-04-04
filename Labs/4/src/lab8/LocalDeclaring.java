package lab8;

/**
  * An interface for an AST node that declares a local variable.
  * NB:  A parameter is also treated as a local variable.  Such declarations
  * have type but no modifiers.
  */

public interface LocalDeclaring extends SymDeclaring {
   public TypeAttrs getType();
}
