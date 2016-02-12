package lab2;
import autogen.*;
import java_cup.runtime.*;
import common.Listing;

/**
  * Answer to question 1:


  * Answer to question 2:


  * Answer to question 4:

 */

public class RecursiveDescent {

   public RecursiveDescent() {
      Scanner.init();
	
      Program();
      
   }
  
   void Program(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   File();
	   }else{
		   oops("no" + " " + t + " " + "Program");
	   }
   }
   
   void File(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   Lists();
	   }else{
		   oops("no" + " " + t + " "+" File");
	   }
   }

   void Lists(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   List();
		   Lists_Two();
	   }else{
		   oops("no" + " " + t+ " " + "Lists");
	   }
   }
   
   void Lists_Two(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   List();
		   Lists_Two();  
	   }else if(t.sym == sym.EOF){ //lamda
	   }else{
		   oops("no" + " " + t+ " " + "Lists_Two");
	   }
   }
   
   void List(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   Scanner.advance();
		   Expression();
		   t = Scanner.peek();
		   if (t.sym == sym.rparen){
			   Scanner.advance();
		   }else{
			   oops("no rparen" + " " + t+ " " + "List"); 
		   }
	   }else{
		   oops("no" + " " + t+ " " + "List");
	   }
   }
   
   void Expression(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.plus){
		   Scanner.advance();
		   Operand();
		   Operand();
	   }else if(t.sym == sym.minus){
		   Scanner.advance();
		   Operand();
		   Operand();
	   }else if(t.sym == sym.times){
		   Scanner.advance();
		   Operand();
		   Operand();
	   }else if(t.sym == sym.negate){
		   Scanner.advance();
		   Operand();
	   }else if(t.sym == sym.sum){
		   Scanner.advance();
		   Operands();
	   }else if(t.sym == sym.product){
		   Scanner.advance();
		   Operands();
	   }else if(t.sym == sym.mean){
		   Scanner.advance();
		   Operands();
	   }else{
		   oops("no" + " " + t+ " " + "Expression");
	   }
   }
   
   void Operand(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   Atom();
	   }else{
		   oops("no" + " " + t+ " " + "Operand");
	   }
   }
   
   void Operands(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   Operand();
		   Operands_Two();
	   }else{
		   oops("no" + " " + t+ " " + "Operands");
	   }
   }
   
   void Operands_Two(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   Operand();
		   Operands_Two();
		   t = Scanner.peek();
	   }else if(t.sym == sym.rparen){
	   }else{
		   oops("no" + " " + t+ " " + "Operands_Two");
	   }
   }
   
   void Atom(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		  List();
	   }else if (t.sym == sym.number){
		   Scanner.advance();
   	   }else{
		   oops("no" + " " + t+ " " + "Atom");
	   } 
   }
   
   void oops(String s) {
      System.err.println("Error: " + s);
      System.exit(-1);
   }
   
   protected void expect(Symbol sym) {
	      expect(sym, "Expected symbol #" +sym + " (as defined in sym.java)");
	   }
	   protected void expect(Symbol sym, String message) {
	      if (Scanner.peek() != sym) oops(message);
	      else Scanner.advance(); 
	   }
}
