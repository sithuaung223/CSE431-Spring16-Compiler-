package studio8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab8.*;

/** Solution authored by:
 * 
 *  ( team members' names )
 *
 */

public class TeamSymtab extends Symtab implements SymtabInterface {

	List<Map<String, SymInfo>> symbolTables = new ArrayList<Map<String, SymInfo>>();

	public TeamSymtab(){
		symbolTables.add(new HashMap<String, SymInfo>());
	}

	int currentNestLevel = 0;
	/** Should never return a negative integer 
	 */
	public int getCurrentNestLevel() {
		return currentNestLevel;
	}

	/** Opens a new scope, retaining outer ones */

	public void incrNestLevel() {
		symbolTables.add(new HashMap<String, SymInfo>());
		

		currentNestLevel++;
	}

	/** Closes the innermost scope */

	public void decrNestLevel() {
		symbolTables.remove(currentNestLevel);
		currentNestLevel--;
	}

	/** Enter the given symbol information into the symbol table.  If the given
	 *    symbol is already present at the current nest level, do whatever is most
	 *    efficient, but do NOT throw any exceptions from this method.
	 */

	public void enter(String s, SymInfo info) {
		Map<String, SymInfo> symbolTable = symbolTables.get(currentNestLevel);

		symbolTable.put(s, info);
	}

	/** Returns the information associated with the innermost currently valid
	 *     declaration of the given symbol.  If there is no such valid declaration,
	 *     return null.  Do NOT throw any excpetions from this method.
	 */

	public SymInfo lookup(String s) {
		SymInfo symbol = null;

		for (int i = currentNestLevel; i>=0; i--){
			Map<String, SymInfo> symbolTable = symbolTables.get(i);

			symbol = symbolTable.get(s);
			if (symbol != null){
				break;
			}
		}
		return symbol;
	}

}
