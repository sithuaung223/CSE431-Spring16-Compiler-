/**
 * CSE 431 Lab4
 * 
 * Evan Schwartzman
 * Si Thu Aung
 * 
 * We used the course symbol table and the course parser
 * 
 * A few notes:
 * Our implementation made a few different design decisions than the reference.
 * 
 * 1. We think these were bugs that the reference didn't do this, but ours throws an error if the same method or class is declared in the same scope
 * 
 * 2. So that we do not throw an error if two distinct classes have fields with the same name we put the field's class name in its
 * 	  symInfo object so if a field is already in the table, we can check if it is also in the same class before we throw an error. 
 *    Also to facilitate this, we use the fact that in the parser, with f being the field node, 
 *    f.getParent().getParent().getParent().getParent().toString() will always equal the class node, because of how the parser is structured,
 *    and also with this parser, the fourth word of f.getParent().getParent().getParent().getParent().toString() will always be the class name.
 *    We use all of this information to do the comparisons when a field with the same name is declared in two different classes (shouldn't fail because each class is its own "table")
 *    
 *    We do something almost identical to handle methods in different classes with the same name...
 *    
 * 3. We also added a place to put the scope of a variable in our symbol object so that two local declarations with the same name but different scopes would not fail
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
		   symbolInfo info = new symbolInfo(n, null, d.getMods(), sti.getCurrentNestLevel(), d.getName());
		   sti.enter(d.getName(), info);
		   visitChildren(n);
	   }
   }
   
   /**
    * 1. Check if f is in symbol table - fail if it is in there (unless it is in there under a different class)
    * 2. Insert f into symbol table (append this)
    */
   public void visit(FieldDeclaring f) {
	   AbstractNode n = (AbstractNode) f;
	   String[] classOfField = n.getParent().getParent().getParent().getParent().toString().split(" "); 
	   
	   String className = ((symbolInfo) sti.lookup(classOfField[3])).checkClass();
	   
	   if(sti.lookup("this." +f.getName()) != null && (className == ((symbolInfo) sti.lookup("this." +f.getName())).checkClass())){
		   sti.err((AbstractNode) f, "Duplicate Field Declaration");
	   }else{
		   symbolInfo info = new symbolInfo(n, f.getType(), f.getMods(), sti.getCurrentNestLevel(), className);
		   sti.enter("this." +f.getName(), info);
	   }
   }
   
   /**
    * 1. Check if l is in symbol table - fail if it is in there (unless it is on different scope level)
    * 2. Insert l into symbol table
    * 3. Decorate Tree
    */
   public void visit(LocalDeclaring l) {
	   if(sti.lookup(l.getName()) != null && (((symbolInfo) sti.lookup(l.getName())).getScopeLevel() == sti.getCurrentNestLevel())){
		   sti.err((AbstractNode) l, "Duplicate Local Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) l;
		   symbolInfo info = new symbolInfo(n, l.getType(), null, sti.getCurrentNestLevel(), null);
		   sti.enter(l.getName(), info);
		   l.setSymInfo(info);
	   }
   }
   
   /**
    * 1. Check if r is in symbol table, also check it with this. + r - fail if it is not in there
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
    * 1. Check if m is in symbol table - fail if it is in there (unless in different class)
    * 2. Insert m into symbol table
    * 3. Pass visitor on to parameters (scope should be incremented by 1)
    * 4. Pass visitor on to method body (concerning scope, treat as a block)
    */
   public void visit(MethodDeclaring m) {
	   AbstractNode n = (AbstractNode) m;
	   String[] classOfMethod = n.getParent().getParent().getParent().getParent().toString().split(" ");
	   
	   String className = ((symbolInfo) sti.lookup(classOfMethod[3])).checkClass();
	   
	   if(sti.lookup(m.getName()) != null && (className == ((symbolInfo) sti.lookup(m.getName())).checkClass())){
		   sti.err((AbstractNode) m, "Duplicate Method Declaration");
	   }else{
		   symbolInfo info = new symbolInfo(n, m.getType(), null, sti.getCurrentNestLevel(), className);
		   sti.enter(m.getName(), info);
		   
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
    * added a getScopeLevel method to make sure local declarations with the same name are accepted if on different scope level...
    * added a toString method
    * added a checkClass method to make sure field, method declarations with the same name are accepted if in different classes...
    *
    */
   class symbolInfo implements SymInfo{
	   public AbstractNode node;
	   public TypeAttrs type;
	   public ModsAttrs mods;
	   public int scope;
	   public String className;
	   
		public symbolInfo(AbstractNode n, TypeAttrs t, ModsAttrs m, int s, String c){
			this.node = n;
			this.type = t;
			this.mods = m;
			this.scope = s;
			this.className = c;
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
		 * Registers are stubs for now - hard code -1 just to be consistent at this point...
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
		
		public String checkClass(){
			return className;
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