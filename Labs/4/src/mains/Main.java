package mains;
import submit.SymtabVisitor;
import common.Listing;
import common.OpenFile;
import coursesolutions.courseparser.*;
import lab7.*;
import lab8.*;

/** The main for lab 11. This builds the AST through the parse, and then
 * dumps the AST to System.out.  The symbol table visitor is then invoked
 * using BuildSymtab.getSymtab().
 */
public class Main {
	public static void main(String args[]) throws Exception {

		java_cup.runtime.lr_parser parser = SymtabVisitor.getParser(args);

		new Listing(System.out);
		AbstractNode root = (AbstractNode) parser.parse().value;

		System.out.println("\nRaw AST (with node numbers)");
		root.accept(new DumpNodeVisitor(System.out));

		System.out.println("Building Symbol table");
		ReflectiveVisitor  rfv = SymtabVisitor.getVisitor();
		root.accept(rfv);

		// Print out AST with references now connected to symtab entries
		root.accept(new DumpNodeVisitor(System.out));
	}

}
