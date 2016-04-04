package studio8;

import lab8.*;

/** Solution authored by:
 * 
 *  ( team members' names )
 *
 */

public class TeamSymtab extends Symtab implements SymtabInterface {
	
	/** Should never return a negative integer 
	 */
	
	public int getCurrentNestLevel() {
		return -1;  // you must fix this
	}
	
	/** Opens a new scope, retaining outer ones */
	
	public void incrNestLevel() {
		
	}
	
	/** Closes the innermost scope */
	
	public void decrNestLevel() {
		
	}
	
	/** Enter the given symbol information into the symbol table.  If the given
	 *    symbol is already present at the current nest level, do whatever is most
	 *    efficient, but do NOT throw any exceptions from this method.
	 */
	
	public void enter(String s, SymInfo info) {
		
	}
	
	/** Returns the information associated with the innermost currently valid
	 *     declaration of the given symbol.  If there is no such valid declaration,
	 *     return null.  Do NOT throw any excpetions from this method.
	 */
	
	public SymInfo lookup(String s) {
		return null;
	}

}
