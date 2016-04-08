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
   //return new CourseSymtabVisitor(new CourseBuildSymtab());
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
   
   /*
    * increase scope +1
    * decrease scope -1
    */
   public void visit(BlockIsh b) {
	   sti.incrNestLevel();
	   AbstractNode n = (AbstractNode) b;
	   visitChildren(n);
	   sti.decrNestLevel();
   }
   
   /*
    * check if d in symbol table - fail if it is in there
    * insert d into symbol table
    * pass visitor on to d's children
    */
   public void visit(ClassDeclaring d) {
	   if(sti.lookup(d.getName()) != null ){
		   sti.err((AbstractNode) d, "Duplicate Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) d;
		   typeVisitor tv = new typeVisitor(n, null, d.getMods(), sti.getCurrentNestLevel());
		   sti.enter(d.getName(), tv);
		   visitChildren(n);
	   }
   }
   
   /*
    * check if f in symbol table - fail if it is in there - append this
    * insert f into symbol table - append this
    */
   public void visit(FieldDeclaring f) {
	   if(sti.lookup("this." +f.getName()) != null){
		   sti.err((AbstractNode) f, "Duplicate Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) f;
		   typeVisitor tv = new typeVisitor(n, f.getType(), f.getMods(), sti.getCurrentNestLevel());
		   sti.enter("this." +f.getName(), tv);
	   }
   }
   
   /*
    * check if l in symbol table - fail if it is in there
    * insert l into symbol table
    */
   public void visit(LocalDeclaring l) {
	   if(sti.lookup(l.getName()) != null && (((typeVisitor) sti.lookup(l.getName())).getScopeLevel() == sti.getCurrentNestLevel())){
		   sti.err((AbstractNode) l, "Duplicate Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) l;
		   typeVisitor tv = new typeVisitor(n, l.getType(), null, sti.getCurrentNestLevel());
		   sti.enter(l.getName(), tv);
		   l.setSymInfo(tv);
	   }
   }
   
   /*
    * check if r in symbol table - fail if it is not in there
    */
   public void visit(LocalReferencing r) {
	   if(sti.lookup(r.getId()) == null){
		   if(sti.lookup("this."+r.getId()) == null){
			   sti.err((AbstractNode) r, "Undefined Reference");
		   }else{
			  SymInfo i = sti.lookup("this."+r.getId());
			  r.setSymInfo(i);
		   }
	   }else{
		   SymInfo i = sti.lookup(r.getId());
		   r.setSymInfo(i);
		   
	   }
		  		   
   }
   
   /*
    * check if m in symbol table - fail if signature is in there
    * insert m into symbol table
    * pass visitor on to parameters (scope = m+1)
    * method body = block node
    */
   public void visit(MethodDeclaring m) {
	   if(sti.lookup(m.getName()) != null && (((typeVisitor) sti.lookup(m.getName())).getScopeLevel() == sti.getCurrentNestLevel())){
		   sti.err((AbstractNode) m, "Duplicate Declaration");
	   }else{
		   AbstractNode n = (AbstractNode) m;
		   typeVisitor tv = new typeVisitor(n, m.getType(), null, sti.getCurrentNestLevel());
		   sti.enter(m.getName(), tv);
		   sti.incrNestLevel();
		   visitChildren(m.getParams());
		   visitChildren(m.getBody());
		   
	   }
   }
   
}

   class typeVisitor implements SymInfo{
		   public AbstractNode node;
		   public TypeAttrs type;
		   public ModsAttrs mods;
		   public int scope;
		   
		public typeVisitor(AbstractNode n, TypeAttrs t, ModsAttrs m, int s ){
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
			return (" "+node + ": type " + type + " mods " + mods );
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