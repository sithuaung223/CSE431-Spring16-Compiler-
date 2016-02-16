/**
 * CSE 431 Lab2
 * 
 * Evan Schwartzman
 * Si Thu Aung
 */

package lab2;
import autogen.*;
import java_cup.runtime.*;
import common.Listing;

/**
  * Answer to question 1:
  
Program
	::= File
	;

File   
	::= Lists
	;

Lists
	::= List Lists_Two
	;

Lists_Two
	::= List Lists_Two
	|   lamda
	;

List
	::= lparen Expression rparen
	;

Expression
	::= plus    Operand Operand
	|   minus   Operand Operand
	|   times   Operand Operand
	|   negate  Operand
	|   sum     Operands
	|   product Operands
	|   mean    Operands
	;

Operand
	::= Atom
	;

Operands
	::= Operand Operands_Two
	;

Operands_Two
	::= Operand Operands_Two
	|   lamda
	;

Atom
	::= List
	|   number
	;

  * Answer to question 2:

Non Terminal:	First()                                                  Follow()			

Program             (                                                           -
File                (                                                           $
Lists               (                                                           $
Lists_Two           (                                                           $
List                (                                                       (, $, ), number
Expression          plus, minus, times, negate, sum, product, mean              )
Operand             (, number                                               (, ), number
Operands            (, number                                                   )
Operands_Two        (, number                                                   )
Atom                (, number                                               (, ), number

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
		   oops("Expected: left parenthesis in Program");
	   }
   }
   
   int File(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   return Lists();
	   }else{
		   oops("Expected: left parenthesis in File");
	   }
	   return 0;
   }

   int Lists(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   int l = List();
		   return Lists_Two(l);
	   }else{
		   oops("Expected: left parenthesis in Lists");
	   }
	   return 0;
   }
   
   int Lists_Two(int a){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   int l = List();
		   return Lists_Two(l);  
	   }else if(t.sym == sym.EOF){ //lamda
	   }else{
		   oops("Expected: left parenthesis or lambda in Lists_Two");
	   }
	   return 0;
   }
   
   int List(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   Scanner.advance();
		   int r = Expression();
		   t = Scanner.peek();
		   if (t.sym == sym.rparen){
			   System.out.print(r);
			   Scanner.advance();
		   }else{
			   oops("Expected: right parenthesis in List"); 
		   }
	   }else{
		   oops("Expected: left parenthesis in List");
	   }
	   return 0;
   }
   
   int Expression(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.plus){
		   Scanner.advance();
		   int o = Operand();
		   int o2 = Operand();
		   return o+o2;
	   }else if(t.sym == sym.minus){
		   Scanner.advance();
		   int o = Operand();
		   int o2 = Operand();
		   return o-o2;
	   }else if(t.sym == sym.times){
		   Scanner.advance();
		   int o = Operand();
		   int o2 = Operand();
		   return o*o2;
	   }else if(t.sym == sym.negate){
		   Scanner.advance();
		   int o = Operand();
		   return (-1)*o;
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
		   oops("Error in Expression");
	   }
	   return 0;
   }
   
   int Operand(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   return Atom();
	   }else{
		   oops("Expected: left parenthesis or number in Operand");
	   }
	   return 0;
   }
   
   void Operands(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   Operand();
		   Operands_Two();
	   }else{
		   oops("Expected: left parenthesis or number in Operands");
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
		   oops("Expected: left parenthesis or number in Operands_Two");
	   }
   }
   
   int Atom(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		  return List();
	   }else if (t.sym == sym.number){
		   Scanner.advance();
		   return (int) t.value;
   	   }else{
		   oops("Expected: left parenthesis or number in Operand");
	   } 
	   return 0;
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