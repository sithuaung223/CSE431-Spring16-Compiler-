package studio8;
import lab8.*;
import timing.TimingChecks;

/** The main for lab 4. This builds the AST through the parse, and then
  * dumps the AST to System.out.  The symbol table visitor is then invoked
  * using BUildSymtab.getSymtab().
  */
public class Main implements GensSymtab {

	/** This must be the method used to return an instance of the symbol table
	 *   Change this to return new TeamSymtab() when you want to test your solution
	 *
	 */
	
	public  SymtabInterface genSymtab() {
		
		//return new TeamSymtab();
		// The following will try my solution
		return new coursesolutions.CourseBuildSymtab(200000, 511);
	}

	public static void main(String args[]) throws Exception {

		String teamName = "Fill this in";
		System.out.println("Studio 4 for team " + teamName);
		
		new TimingChecks(new Main()).run();  // also runs correctness checks
	}
}
