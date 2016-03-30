package submit;
import autogen.Parser;
import autogen.Yylex;
import common.OpenFile;

import coursesolutions.*;
import coursesolutions.courseparser.CourseParser;
import coursesolutions.courseparser.CourseScanner;
import lab7.*;
import lab8.*;

/**
  * You must implement the visitor and the symbol table
  */

public class SymtabVisitor extends NodeVisitor {

   /** This method lets us keep Main as-is before and after your implementation.
     *  Suggestion:  develop as follows.
     *    Step 1:  replace the return below with
     *      return new SymtabVisitor(new ClassBuildSymtab());
     *      This will use your visitor but the class symbol table implementation
     *    Step 2:  [ not necessary but if you want] replace the return below with
     *      return new SymtabVisitor(new BuildSymtab());
     *      This will use your visitor and your symbol table implementation
     */
   
   public static ReflectiveVisitor getVisitor() { 
      return new CourseSymtabVisitor(new CourseBuildSymtab());
   }

   public static java_cup.runtime.lr_parser getParser(String[] args) {
	   // FIXME
	   // If you want to use your parser, 
	   //     then use the following line instead of the one below it:
	   // return new Parser(new Yylex(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
	   return new CourseParser(new CourseScanner(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
   }
   
   private SymtabInterface sti;
   public SymtabVisitor(SymtabInterface sti) { this.sti = sti; }

   /** This method will cause the whole AST to be visited, but nothing will happen.
     * It's good default behavior, but you'll need methods to handle various node types.
     */
   public void defaultVisit(Object o) {
      AbstractNode n = (AbstractNode) o;
      visitChildren(n);
      sti.out((AbstractNode)o, "hello");
   }
   

}
   /** OPTIONAL (or from Studio if you want): Your symbol table implementation */
   class BuildSymtab extends Symtab implements SymtabInterface {

      public void incrNestLevel() { out("Nest level now " + getCurrentNestLevel()); }
      public void decrNestLevel() { out("Nest level now " + getCurrentNestLevel()); }
      public int getCurrentNestLevel() { return 0; }
      public SymInfo lookup(String id) { return  null; }
      public void enter(String id, SymInfo s) { }
   }
