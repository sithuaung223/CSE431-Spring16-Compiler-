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
		String paramType = i.getParams().getNodeType().toString();		
		emitComment(paramType);
	}

}
