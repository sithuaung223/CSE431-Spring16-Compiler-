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
	public void visit(ParamIsh p) {
		emitComment("do nothing at paramish");

	}
	
	public void visit(MethodDeclaring i) {
		String mod= i.getMods().toString(); // get method modifier 
		String type= i.getType().getTypeString();// get method type
		String name= i.getName(); // get method name
		String param= "()";
		int count=0;
		
		if(i.getParams().getChild() != null){
			AbstractNode s = i.getParams().getChild();
			LocalDeclaring p = (LocalDeclaring) s;
			emitComment("paramdecarling"+count);
			p.getSymInfo().setRegister(count);
			
			param="("+p.getType();
			emitComment(param);
			
			//multiple params
			while(s.getSib() != null){
				count++;
				p = (LocalDeclaring) s.getSib();
				emitComment("paramdecarlings"+count);
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
		emitComment("in the method");
		
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
		emitComment("compute");
		//emitComment(c.getOperation());
		emit("i"+c.getOperation());
	}
	public void visit(AssignIsh a){
		//visit children node before assign
		emitComment("before visiting assign children");
		emitComment("right child: "+ a.getAssignTypeNode());
		
		//get right child
		dispatch(a.getSubjectNode());
		
		//store to variable
		LocalReferencing assign = (LocalReferencing) a.getAssignTypeNode();	
		if(a.getAssignTypeNode().getNodeType().toString().equals("I")){	
			emit("istore " + assign.getSymInfo().getRegister());
		}else if(a.getAssignTypeNode().getNodeType().toString().equals("Z")){
			emit("istore " + assign.getSymInfo().getRegister());
		}else{
			emit("astore " + assign.getSymInfo().getRegister());
		}

	}

	public void visit(ConstantInt ci){
		//store constant
		emit("ldc "+ci.getVal());
		visitChildren((AbstractNode) ci);
	}
	
	public void visit(ConstantString cs){
		//store constant
		emit("ldc " +"\""+cs.getVal()+"\"");
		visitChildren((AbstractNode) cs);
	}
	
	public void visit(ConstantBool cb){
		//store constant
		if(cb.getVal()){
			emit("ldc 1");
		}else{
			emit("ldc 0");
		}
		
		visitChildren((AbstractNode) cb);
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
				
				if(l.getSymInfo().getType().toString().equals("I") || l.getSymInfo().getType().toString().equals("Z")){
					emit("iload "+ count);
					count++;
				}else{
					emit("aload "+ count);
					count++;
				}
				AbstractNode z = n.getSib().getChild();
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
			
		}
		emit("invokevirtual java/io/PrintStream/"+ fr.getFieldName()+param+ fr.getResultingType());
	}
	
	public void visit(StaticReferencing sr){
		emitComment("staticReferencing"+ sr.getClassName());
		AbstractNode n = (AbstractNode) sr;
		String param = "()";
		String className= sr.getClassName().toString();
		if(n.getSib() != null){
			if(n.getSib().getChild() != null){
				visitChildren((AbstractNode) n.getSib());
				emitComment("Name: "+ n.getSib().getChild().getNodeType().toString());
				
				AbstractNode z = n.getSib().getChild();
				String paramTypes = z.getNodeType().toString();
				
				while(z.getSib() != null){
					
					paramTypes = paramTypes + z.getSib().getNodeType().toString();
					z = z.getSib();	
				}
				param = "("+ paramTypes + ")";
			}
			//when there is a parameters
			emit("invokestatic " + className.substring(1, className.length()-1)+"/"+ sr.getFieldName()+param+sr.getResultingType());
		}
		else{
			//when there is no parameters
			emit("getstatic "+ className.substring(1, className.length()-1)+"/"+sr.getFieldName()+" "+sr.getResultingType());
		}
		visitChildren((AbstractNode) sr);
	}
	
	public void visit(LocalDeclaring ld){
		AbstractNode n = (AbstractNode) ld;
		emitComment("localdecarling"+ ld.getName());
		ld.getSymInfo().setRegister(n.getNodeNum());
		visitChildren((AbstractNode) ld);
	}
	public void visit(LocalReferencing lr){
		emitComment("id"+ lr.getId());
		if(lr.getSymInfo().getType().toString().equals("I")){
			emitComment("I in parameter"+ lr.getSymInfo().toString());
			emit("iload " + lr.getSymInfo().getRegister());
		}else if(lr.getSymInfo().getType().toString().equals("Z")){
			emitComment("bool in parameter"+ lr.getSymInfo().toString());
			emit("iload " + lr.getSymInfo().getRegister());
		}else{
			emitComment("not I in parameter"+ lr.getSymInfo().toString());
			emit("aload " + lr.getSymInfo().getRegister());
		}
//		AbstractNode lrNode= (AbstractNode) lr;
//		emitComment("children of LR:" +lrNode.getChild().getName());
		visitChildren((AbstractNode) lr);
	}
	public void visit(InvokeIsh i){
		AbstractNode ia = (AbstractNode) i;
		//dispatch(ia.getChild().getSib());
		dispatch(ia.getChild());

	}
	public void visit(IfIsh If){
		AbstractNode bo = (AbstractNode) If;
		
		emitComment("if predicate"+ If.getPredicate().getNodeType().toString());
		emit("ifStart: ");
		
		dispatch(If.getPredicate());
		
		String[] bool = bo.getChild().whatAmI().split("Node");
		
		if(bool[0].equals("Bool")){
			String[] j = bo.getChild().getName().split(" ");
			if(j[1].equals("false")){
				emit("goto if_falsePart");
			}else{
				emit("goto if_truePart");
			}
		}else{
			CompareIsh cmp= (CompareIsh) If.getPredicate();
			emitComment("IN If:"+cmp.getCompare());
			emit("if"+cmp.getCompare()+" if_truePart");
			emit("goto if_falsePart");
		}
		
		
		
		
		emitComment("if true part" + If.getTruePart().getChild().getName());	
		emit("if_truePart:");
		dispatch(If.getTruePart());
		emit("goto endIf");
		
		if(If.getFalsePart() != null){
			emitComment("if flase part" + If.getFalsePart().getName());
			emit("if_falsePart:");
			dispatch(If.getFalsePart());
			emit("goto endIf");			
		}

		
		emit("endIf: ");
	}
	
	public void visit(WhileIsh wh){
	
		
		emitComment("while predicate"+ wh.getPredicate().getName());
		emit("whileStart: ");
		dispatch(wh.getPredicate());
		
		//handle while predicate here
		CompareIsh cmp= (CompareIsh) wh.getPredicate();
		emitComment("IN WHILE:"+cmp.getCompare());
		emit("if"+cmp.getCompare()+" while_truePart");
		emit("goto while_falsePart");
		
		emitComment("while true part"+ wh.getBody().toString());
		emit("while_truePart: ");
		dispatch(wh.getBody());
		emitComment("while loop back");
		emit("goto whileStart");
		
		emitComment("while false part");
		emit("while_falsePart: ");
	}
	public void visit(CompareIsh cmp){
		AbstractNode Acmp= (AbstractNode) cmp;
		visitChildren(Acmp);
		emit("isub");
//		emit("if_icmp"+cmp.getCompare()+" truePart");
//		emit("goto falsePart");
	}
	


}