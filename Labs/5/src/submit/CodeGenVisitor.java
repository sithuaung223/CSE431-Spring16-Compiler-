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
		String mod= i.getMods().toString(); // get method modifier 
		String type= i.getType().getTypeString();// get method type
		String name= i.getName(); // get method name
		String param= "()";
		
		
		
		if(i.getParams().getChild() != null){
			AbstractNode s = i.getParams().getChild();
			param="("+i.getParams().getChild().toString().charAt(6);
			emitComment(param);
			
			//multiple params
			while(s.getSib() != null){
				param = param+s.getSib().toString().charAt(6);
				s = s.getSib();
			}
			
			param = param + ")";
			
		}
		
		
		
		if(i.getParams().getNodeType()==null){
		}
		else {
			emitComment(i.getParams().getName());
			String paramType = i.getParams().getNodeType().toString();		
			emitComment("param"+paramType);
		}
	
		
		//start method
		emit( ".method "+mod+" "+name+param+type);
		//TODO EXTRA CREDIT: determine amount of stack and local variables
		emit(".limit locals 500");
		emit(".limit stack 500");
		
		//visit method body 
		visitChildren((AbstractNode)i);
		
		//return and end the method
		emit("return");
		emit(".end method\n");
		
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
		//emitComment(a.getAssignTypeNode().getNodeNum()+"");
		
		LocalReferencing assign = (LocalReferencing) a.getAssignTypeNode();
		
		if(a.getAssignTypeNode().getNodeType().toString().equals("I")){	
			emit("istore " + assign.getSymInfo().getRegister());
			emit("iload " + assign.getSymInfo().getRegister());
		}else{
			emit("astore " + assign.getSymInfo().getRegister());
			emit("aload " + assign.getSymInfo().getRegister());
		}
		
	
	}

	public void visit(ConstantInt ci){
		//store constant
		emit("ldc "+ci.getVal());
		visitChildren((AbstractNode) ci);
	}
	
	public void visit(FieldReferencing fr){
		visitChildren((AbstractNode) fr);
		//TODO have to get class name dynamically
		AbstractNode n = (AbstractNode) fr;
		String param = "()";

		
		int count = 0;
		if(n.getSib() != null){
			if(n.getSib().getChild() != null){
				LocalReferencing l = (LocalReferencing) n.getSib().getChild();
				param = "(" + l.getSymInfo().getType();
				emit("iload "+ count);
				count++;
				AbstractNode z = n.getSib().getChild();
				while(z.getSib() != null){
					emit("iload "+ count);
					count++;
					LocalReferencing zl = (LocalReferencing) z;
					param = param + zl.getSymInfo().getType();
					z = z.getSib();	
				}
			}
			param = param + ")";
		}
		emit("invokevirtual java/io/PrintStream/"+ fr.getFieldName()+param+ fr.getResultingType());
	}
	
	public void visit(StaticReferencing sr){
		AbstractNode n = (AbstractNode) sr;
		String param = "()";
		if(n.getSib() != null){
			if(n.getSib().getChild() != null){
				LocalReferencing l = (LocalReferencing) n.getSib().getChild();
				param = "(" + l.getSymInfo().getType();
				
				AbstractNode z = n.getSib().getChild();
				
				if(l.getSymInfo().getType().toString().equals("I")){
					emit("iload " + l.getSymInfo().getRegister());
				}else{
					emit("aload " + l.getSymInfo().getRegister());
				}
				
				while(z.getSib() != null){
					LocalReferencing zl = (LocalReferencing) z;
					if(zl.getSymInfo().getType().toString().equals("I")){
						emit("iload " + zl.getSymInfo().getRegister());
					}else{
						emit("aload " + zl.getSymInfo().getRegister());
					}
					
					param = param + zl.getSymInfo().getType();
					z = z.getSib();	
				}
			}
			param = param + ")";
		}
		
		String className= sr.getClassName().toString();
		if(className.substring(1,5).equals("java")){
			emit("getstatic "+ className.substring(1, className.length()-1)+"/"+sr.getFieldName()+" "+sr.getResultingType());
		}else{
			emit("invokestatic " + className.substring(1, className.length()-1)+"/"+ sr.getFieldName()+param+sr.getResultingType());	
		}
		visitChildren((AbstractNode) sr);
	}
	
	public void visit(LocalDeclaring ld){
		AbstractNode n = (AbstractNode) ld;
		ld.getSymInfo().setRegister(n.getNodeNum());
		visitChildren((AbstractNode) ld);
	}
	
	/*
	public void visit(LocalReferencing lr){
		
		AbstractNode n = (AbstractNode) lr;
		lr.getSymInfo().setRegister(n.getNodeNum()); 
		
		visitChildren((AbstractNode) lr);
		
		
		emit("istore " + lr.getSymInfo().getRegister());
		emit("iload " + lr.getSymInfo().getRegister());
		emitComment(lr.getSymInfo().getRegister() + "");
		
	}
	*/
	
	
	
	/**
	public void visit(IfIsh i){
		
		emitComment("1 " + i.getFalsePart().getChild().getChild().toString());
		emitComment("2 " +  i.getTruePart().toString());
		emitComment("3 " + i.getPredicate().getName());
		
		visitChildren((AbstractNode) i);
		
		emit("if");
		
		
		
	}
	
	**/
	
	/**
	public void visit(ReturnIsh r){
		AbstractNode w = (AbstractNode) r;
		w.

		
		emit("return" + r.)
	}
	**/

}