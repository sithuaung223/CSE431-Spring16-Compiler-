/**
 * Evan Schwartzman
 * Si Thu Aung
 * 
 * CSE431 Lab5 - Final Project
 * 
 * We used the course AST, and the course SymbolTable
 * 
 * Passes all test files, except field which is supposed to error out, as noted in the file, because there is a missing class,
 * but it still generates the correct jasmin code, which is what is supposed to happen. Additionally, secret.canyoudothat and
 * secret.whataboutthis need to be modified to run, but once the syntax/package etc. stuff is fixed, they pass.
 * 
 */

package submit;
import coursesolutions.*;
import coursesolutions.courseparser.CourseParser;
import coursesolutions.courseparser.CourseScanner;
import lab7.*;
import lab8.*;

import java.io.PrintStream;

import autogen.*;
import common.OpenFile;

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
	public void visit(ParamIsh p) {
		emitComment("do nothing at paramish");
	}
	
	public void visit(MethodDeclaring i) {
		String mod= i.getMods().toString(); 
		String type= i.getType().getTypeString();
		String name= i.getName();
		String param= "()";
		int count=0;
		
		//Check if there are params
		if(i.getParams().getChild() != null){
			AbstractNode s = i.getParams().getChild();
			LocalDeclaring p = (LocalDeclaring) s;
			p.getSymInfo().setRegister(count);	
			param="("+p.getType();
			
			//Multiple params
			while(s.getSib() != null){
				count++;
				p = (LocalDeclaring) s.getSib();
				p.getSymInfo().setRegister(count);
				param = param+p.getType();
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
		emit( ".method "+mod+" "+name+param+type);
		emit(".limit locals 500");
		emit(".limit stack 500");		
		visitChildren((AbstractNode)i);		
		emit("return");
		emit(".end method\n");		
	}
	
	public void visit(ComputeIsh c){
		visitChildren((AbstractNode)c);
		boolean trigger = true;
		AbstractNode newChild = (AbstractNode) c;
		AbstractNode z = newChild.getChild();
		String[] h = z.whatAmI().toString().split("Node");
		String[] h2 =  newChild.whatAmI().toString().split("Node"); 
		
		if(h2[0].equals("ReturnValue")){
			if(h[0].equals("Int")){			
			}else if(h[0].equals("String")){
				trigger = false;
				emit("a"+c.getOperation());		
			}else if(h[0].equals("Ref")){
				LocalReferencing r = (LocalReferencing) z;
				if(r.getSymInfo().getType().toString().equals("I")){		
				}else{
					trigger = false;
					emit("a"+c.getOperation());
				}			
			}
		}
		
		if(trigger == true){
			emit("i"+c.getOperation());
		}
	}
	
	public void visit(AssignIsh a){
		dispatch(a.getSubjectNode());		
		String[] field =  a.getAssignTypeNode().whatAmI().split("Reference");
		if(field[0].equals("Field")){
			visitChildren((AbstractNode) a.getAssignTypeNode());
			LocalReferencing assign1 = (LocalReferencing) a.getAssignTypeNode().getChild();	
			String assign3 = assign1.getSymInfo().getType().toString();
			String assign4 = assign3.substring(1, assign3.length()-1);
			emit("swap");
			emit("putfield " +assign4 +"/"+a.getAssignTypeNode().getChild().getSib().toString()+ 
					" " + a.getAssignTypeNode().getChild().getSib().getSib().getNodeType().toString());
		}else{
			LocalReferencing assign = (LocalReferencing) a.getAssignTypeNode();	
			if(a.getAssignTypeNode().getNodeType().toString().equals("I")){	
				emit("istore " + assign.getSymInfo().getRegister());
			}else if(a.getAssignTypeNode().getNodeType().toString().equals("Z")){
				emit("istore " + assign.getSymInfo().getRegister());
			}else{
				emit("astore " + assign.getSymInfo().getRegister());
			}
		}
	}

	public void visit(ConstantInt ci){
		emit("ldc "+ci.getVal());
		visitChildren((AbstractNode) ci);
	}
	
	public void visit(ConstantString cs){
		emit("ldc " +"\""+cs.getVal()+"\"");
		visitChildren((AbstractNode) cs);
	}
	
	public void visit(ConstantBool cb){
		//Use "1" and "0" as truth values
		if(cb.getVal()){
			emit("ldc 1");
		}else{
			emit("ldc 0");
		}	
		visitChildren((AbstractNode) cb);
	}
	
	public void visit(FieldReferencing fr){
		visitChildren((AbstractNode) fr);
		AbstractNode n = (AbstractNode) fr;
		String param = "()";
		int count = 0;
		
		//Check for params
		if(n.getSib() != null){
			if(n.getSib().getChild() != null){
				LocalReferencing l = (LocalReferencing) n.getSib().getChild();
				param = "(" + l.getSymInfo().getType();
				
				if(l.getSymInfo().getType().toString().equals("I") || l.getSymInfo().getType().toString().equals("Z")){
					emit("iload "+ count);
					count++;
				}else{
					emit("aload "+ count);
					count++;
				}
				AbstractNode z = n.getSib().getChild();
				
				//Multiple params
				while(z.getSib() != null){
					LocalReferencing zl = (LocalReferencing) z;
					if(zl.getSymInfo().getType().toString().equals("I") || l.getSymInfo().getType().toString().equals("Z")){
						emit("iload "+ count);
						count++;
					}else{
						emit("aload "+ count);
						count++;
					}			
					param = param + zl.getSymInfo().getType();
					z = z.getSib();	
				}
				param = param + ")";
			}
			LocalReferencing lrn = (LocalReferencing) n.getChild();
			emit("invokevirtual "+lrn.getSymInfo().getType().toString().substring(1, lrn.getSymInfo().getType().toString().length()-1)+
					"/"+ fr.getFieldName()+param+ fr.getResultingType());
		}else{
			LocalReferencing frn = (LocalReferencing) n.getChild();
			emit("getfield "+frn.getSymInfo().getType().toString().substring(1, frn.getSymInfo().getType().toString().length()-1)+
					"/"+ fr.getFieldName()+" "+ fr.getResultingType());
		}	
	}
	
	public void visit(StaticReferencing sr){
		AbstractNode n = (AbstractNode) sr;
		String param = "()";
		String className= sr.getClassName().toString();
		
		//Check if there are params
		if(n.getSib() != null){
			if(n.getSib().getChild() != null){
				visitChildren((AbstractNode) n.getSib());				
				AbstractNode z = n.getSib().getChild();
				String paramTypes = z.getNodeType().toString();	
				
				//Multiple params
				while(z.getSib() != null){			
					paramTypes = paramTypes + z.getSib().getNodeType().toString();
					z = z.getSib();	
				}
				param = "("+ paramTypes + ")";
			}
			
			//When there are parameters
			emit("invokestatic " + className.substring(1, className.length()-1)+"/"+ sr.getFieldName()+param+sr.getResultingType());
		}
		else{
			//When there are no parameters
			emit("getstatic "+ className.substring(1, className.length()-1)+"/"+sr.getFieldName()+" "+sr.getResultingType());
		}
		visitChildren((AbstractNode) sr);
	}
	
	public void visit(LocalDeclaring ld){
		AbstractNode n = (AbstractNode) ld;
		
		//Set Register
		ld.getSymInfo().setRegister(n.getNodeNum());
		visitChildren((AbstractNode) ld);
	}
	
	public void visit(LocalReferencing lr){
		if(lr.getSymInfo().getType().toString().equals("I")){
			emit("iload " + lr.getSymInfo().getRegister());
		}else if(lr.getSymInfo().getType().toString().equals("Z")){
			emit("iload " + lr.getSymInfo().getRegister());
		}else{
			emit("aload " + lr.getSymInfo().getRegister());
		}
		visitChildren((AbstractNode) lr);
	}
	
	public void visit(InvokeIsh i){
		AbstractNode ia = (AbstractNode) i;
		dispatch(ia.getChild());
	}
	
	//Use unique label names for the if statement to deal with if else if else
	public void visit(IfIsh If){
		AbstractNode bo = (AbstractNode) If;
		emit("ifStart"+bo.getNodeNum()+": ");
		String[] bool = bo.getChild().whatAmI().split("Node");
		
		//Check for predicate being literally "true" or "false"
		if(bool[0].equals("Bool")){
			String[] j = bo.getChild().getName().split(" ");
			if(j[1].equals("false")){
				emit("goto if_falsePart"+bo.getNodeNum());
			}else{
				emit("goto if_truePart"+bo.getNodeNum());
			}
		}else{
			dispatch(If.getPredicate());
			emit("ifeq if_falsePart"+bo.getNodeNum());			
			emit("goto if_truePart"+bo.getNodeNum());		
		}
		emit("if_truePart"+bo.getNodeNum()+":");
		dispatch(If.getTruePart());
		emit("goto endIf"+bo.getNodeNum());
		
		if(If.getFalsePart() != null){
			emit("if_falsePart"+bo.getNodeNum()+": ");
			dispatch(If.getFalsePart());
			emit("goto endIf"+bo.getNodeNum());			
		}	
		emit("endIf"+bo.getNodeNum()+": ");
	}
	
	public void visit(WhileIsh wh){
		AbstractNode bo = (AbstractNode) wh;
		emit("whileStart"+bo.getNodeNum()+": ");
		String[] bool = bo.getChild().whatAmI().split("Node");
		
		//Check for predicate being literally "true" or "false"
		if(bool[0].equals("Bool")){
			String[] j = bo.getChild().getName().split(" ");
			if(j[1].equals("false")){
				emit("goto while_falsePart"+bo.getNodeNum());
			}else{
				emit("goto while_truePart"+bo.getNodeNum());
			}
		}else{
			dispatch(wh.getPredicate());
			emit("ifeq while_falsePart"+bo.getNodeNum());
			emit("goto while_truePart"+bo.getNodeNum());
		}
		emit("while_truePart"+bo.getNodeNum()+": ");
		dispatch(wh.getBody());
		emit("goto whileStart"+bo.getNodeNum());
		emit("while_falsePart"+bo.getNodeNum()+": ");
	}
	
	//Some boolean stuff as well
	public void visit(CompareIsh cmp){
		AbstractNode Acmp= (AbstractNode) cmp;
		visitChildren(Acmp);
		emit("if_icmp"+cmp.getCompare()+" truePart"+Acmp.getNodeNum());
		emit("goto falsePart"+Acmp.getNodeNum());
		emit("truePart"+Acmp.getNodeNum()+": ");
		emit("ldc 1");
		emit("goto endPart"+Acmp.getNodeNum());
		emit("falsePart"+Acmp.getNodeNum()+": ");
		emit("ldc 0");
		emit("goto endPart"+Acmp.getNodeNum());
		emit("endPart"+Acmp.getNodeNum()+": ");
	}
	
	public void visit(AndIsh And){
		AbstractNode ab= (AbstractNode) And;
		dispatch(ab.getChild());
		dispatch(ab.getChild().getSib());
		emit("imul");
	}
	
	public void visit(OrIsh Or){
		AbstractNode ab= (AbstractNode) Or;
		dispatch(ab.getChild());
		dispatch(ab.getChild().getSib());
		emit("iadd");
	}
	
	public void visit(ShortAndIsh sAnd){
		AbstractNode ab= (AbstractNode) sAnd;		
		dispatch(ab.getChild());
		emit("ifeq shortAndJump"+ab.getNodeNum());		
		dispatch(ab.getChild().getSib());
		emit("goto shortAndEnd"+ab.getNodeNum());		
		emit("shortAndJump"+ab.getNodeNum()+": ");
		emit("ldc 0");		
		emit("shortAndEnd"+ab.getNodeNum()+": ");
	}
	
	public void visit(ShortOrIsh sOr){
		AbstractNode ab= (AbstractNode) sOr;
		dispatch(ab.getChild());
		emit("ifne shortOrJump"+ ab.getNodeNum());		
		emit("shortOrJump"+ab.getNodeNum()+": ");
		dispatch(ab.getChild().getSib());
		emit("goto shortOrEnd"+ab.getNodeNum());
		emit("shortOrJump"+ab.getNodeNum()+": ");
		emit("ldc 1");		
		emit("shortOrEnd"+ab.getNodeNum()+": ");
	}

}