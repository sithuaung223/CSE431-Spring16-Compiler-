package submit;
import coursesolutions.*;
import coursesolutions.courseparser.CourseParser;
import coursesolutions.courseparser.CourseScanner;
import lab7.*;
import lab8.*;
import java.io.PrintStream;
import autogen.*;

import common.OpenFile;

/**
 */

public class CodeGenVisitor extends NodeVisitor {

	public static java_cup.runtime.lr_parser getParser(String[] args) {
		// FIXME
		// If you want to use your parser, then use the following line instead
		//    of the one below it:
		// return new Parser(new Yylex(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
		return new CourseParser(new CourseScanner(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
	}

	public static ReflectiveVisitor getSymtabVisitor() { 
		// FIXME
		// If you want to use your own symbol table code, then
		//    replace the line below with something suitable
		return new CourseSymtabVisitor(new CourseBuildSymtab());
	}


	/**
	 * Change this method to return the visitor you want for code generation. As
	 * given to you, it runs the course-sponsored solution
	 * (CourseProjectCodeGenVisitor); Change the return to
	 * "new CodeGenVisitor()" to return an instance of this class instead.
	 * 
	 * @return the visitor for performing code generation
	 */

	public static ReflectiveVisitor getCodeGenVisitor() {
		return new CodeGenVisitor(); // Change this to CodeGenVisitor() to
		// test your code
	}

	public static ReflectiveVisitor getTypeSetVisitor() {
		return new CourseTypeSetVisitor();
	}

	private void emit(String s) {
		PrintStream ps = System.out;
		out(ps, s);
	}

	private void emit(NodeDumpable a, String s) {
		emit("; " + a.dump());
		emit(s);
	}

	private void emitComment(String s) {
		emit("; " + s);
	}

	private void skip(int num) {
		for (int i = 0; i < num; ++i)
			emit("");
	}

	/**
	 * This outputs a standard prelude, with the class extending Object, a dummy
	 * method for main(String[] args) that calls main431 Thus, your test file
	 * must have a static main431 to kick things off
	 */
	public void visit(ClassDeclaring c) {
		
		emitComment("CSE431 automatically generated code file");
		emitComment("");
		emit(c, ".class public TestClasses/" + c.getName());
		emit(".super java/lang/Object");
		emit("; standard initializer\n\n" + ".method public <init>()V\n"
				+ "   aload_0\n"
				+ "   invokenonvirtual java/lang/Object/<init>()V\n"
				+ "   return\n" + ".end method\n\n");
		emitComment("dummy main to call our main because we don't handle arrays");
		skip(2);
		emit(".method public static main([Ljava/lang/String;)V\n"
				+ "   .limit locals 1\n" + "   .limit stack  3\n"
				+ "   invokestatic " + "TestClasses/" + c.getName() + "/main431()V\n"
				+ "   return\n" + ".end method\n\n");
		visitChildren((AbstractNode) c);
	}

	public void defaultVisit(Object o) {
		AbstractNode n = (AbstractNode) o;
		out("Ignoring " + n.dump());
		visitChildren(n);
	}
	
	public void visit(MethodDeclaring i) {
		if(i.getParams().getNodeType()==null){
			
		}
		else {
			String paramType = i.getParams().getNodeType().toString();		
			emitComment(paramType);
		}
		String mod= i.getMods().toString(); // get method modifier 
		String type= i.getType().getTypeString();// get method type
		String name= i.getName(); // get method name
		
		//start method
		emit( ".method "+mod+" "+name+"()"+type);
		//TODO EXTRA CREDIT: determine amount of stack and local variables
		emit(".limit locals 100");
		emit(".limit stack 100");
		
		//visit method body 
		visitChildren((AbstractNode)i);
		
		//return and end the method
		emit("return");
		emit(".end method");
		
	}
	
	public void visit(ComputeIsh c){
		//visit children noded before compute
		visitChildren((AbstractNode)c);
		//compute
		emit("i"+c.getOperation());
	}
	public void visit(AssignIsh a){
		//visit children node before assign
		visitChildren((AbstractNode) a);
		//TODO assign dynamically instead of hardcoded value 1
		emit("istore_" + 1);
		emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
		emit("ldc" + " \"Register 1 equals as of now: \" ");
		emit("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V");
		emit("getstatic java/lang/System/out Ljava/io/PrintStream;");
		emit("iload 1");
		emit("invokestatic java/lang/String/valueOf(I)Ljava/lang/String;");
		emit("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
	
	}

	public void visit(ConstantInt ci){
		//store constant
		emit("ldc "+ci.getVal());
		visitChildren((AbstractNode) ci);
	}
}