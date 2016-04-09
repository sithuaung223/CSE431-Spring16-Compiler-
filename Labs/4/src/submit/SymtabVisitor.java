/**
 * CSE 431 Lab4
 * 
 * Evan Schwartzman
 * Si Thu Aung
 * 
 * Used the course symbol table, course parser
 * 
 */


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
      return new SymtabVisitor(new CourseBuildSymtab());
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
   
   /**
    * 1. Increase scope +1
    * 2. Visit children
    * 3. Decrease scope -1
    */
   public void visit(BlockIsh b) {
	   sti.incrNestLevel();
	   AbstractNode n = (AbstractNode) b;
	   visitChildren(n);
	   sti.decrNestLevel();
   }
   
   /**
    * 1. Check if d is in symbol table - fail if it is in there
    * 2. Insert d into symbol table
    * 3. Pass visitor on to d's children
    */
   public void visit(ClassDeclaring d) {
	   if(sti.lookup(d.getName()) != null ){
		   sti.err((AbstractNode) d, "Duplicate Class Declaration!");
	   }else{
		   AbstractNode n = (AbstractNode) d;
		   symbolInfo info = new symbolInfo(n, null, d.getMods(), sti.getCurrentNestLevel());
		   sti.enter(d.getName(), info);
		   visitChildren(n);
	   }
   }
   
   /**
    * 1. Check if f is in symbol table - fail if it is in there
    * 2. Insert f into symbol table (append this)
    */
   public void visit(FieldDeclaring f) {
	   if(sti.lookup("this." +f.getName()) != null){
		   sti.err((AbstractNode) f, "Duplicate Field Declaration!");
	   }else{
		   AbstractNode n = (AbstractNode) f;
		   symbolInfo info = new symbolInfo(n, f.getType(), f.getMods(), sti.getCurrentNestLevel());
		   sti.enter("this." +f.getName(), info);
	   }
   }
   
   /**
    * 1. Check if l is in symbol table - fail if it is in there
    * 2. Insert l into symbol table
    * 3. Decorate Tree
    */
   public void visit(LocalDeclaring l) {
	   if(sti.lookup(l.getName()) != null && (((symbolInfo) sti.lookup(l.getName())).getScopeLevel() == sti.getCurrentNestLevel())){
		   sti.err((AbstractNode) l, "Duplicate Local Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) l;
		   symbolInfo info = new symbolInfo(n, l.getType(), null, sti.getCurrentNestLevel());
		   sti.enter(l.getName(), info);
		   l.setSymInfo(info);
	   }
   }
   
   /**
    * 1. Check if r is in symbol table - fail if it is not in there
    * 2. Decorate Tree
    */
   public void visit(LocalReferencing r) {
	   if(sti.lookup(r.getId()) == null){
		   if(sti.lookup("this."+r.getId()) == null){
			   sti.err((AbstractNode) r, "Undefined Reference");
		   }else{
			  SymInfo info = sti.lookup("this."+r.getId());
			  r.setSymInfo(info);
		   }
	   }else{
		   SymInfo info = sti.lookup(r.getId());
		   r.setSymInfo(info);
		   
	   }
		  		   
   }
   
   /**
    * 1. Check if m is in symbol table - fail if it is in there
    * 2. Insert m into symbol table
    * 3. Pass visitor on to parameters (scope should be incremented by 1)
    * 4. Pass visitor on to method body (treat as a block concerning scope)
    */
   public void visit(MethodDeclaring m) {
	   if(sti.lookup(m.getName()) != null){
		   sti.err((AbstractNode) m, "Duplicate Method Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) m;
		   symbolInfo tv = new symbolInfo(n, m.getType(), null, sti.getCurrentNestLevel());
		   sti.enter(m.getName(), tv);
		   
		   sti.incrNestLevel();
		   visitChildren(m.getParams());
		   
		   sti.incrNestLevel();
		   visitChildren(m.getBody());
		   sti.decrNestLevel();
		   
	   }
   }
   
}
   /**
    * 
    * symbolInfo implements the SymInfo object
    * 
    * added a getScopeLevel method to return current scope
    * added a toString method
    *
    */
   class symbolInfo implements SymInfo{
		   public AbstractNode node;
		   public TypeAttrs type;
		   public ModsAttrs mods;
		   public int scope;
		   
		public symbolInfo(AbstractNode n, TypeAttrs t, ModsAttrs m, int s ){
			this.node = n;
			this.type = t;
			this.mods = m;
			this.scope = s;
		}
		@Override
		public AbstractNode getDefiningNode() {
			return node;
		}
	
		@Override
		public TypeAttrs getType() {
			return type;
		}
	
		@Override
		public ModsAttrs getMods() {
			return mods;
		}
	
		/*
		 * Just stubs for now
		 */
		@Override
		public int getRegister() {
			return 0;
		}
	
		@Override
		public void setRegister(int reg) {
		}
		
		public String toString(){
			return (node.getNodeNum() + ": type " + type + " mods " + mods + " reg -1" );
		}
	
		public int getScopeLevel(){
			return scope;
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