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

Non Terminal:	| Derives-Lambda| 			First()							| 	Follow()		|
———————————————————————————————————————————————————————————————————————————————————————————————————————————
Program			|	F	 |	  		(										|	  —				|
File			|	F	 |	  		(										|	  $				|
Lists			|	F	 |	  		(										|	  $				|
Lists_Two		|	T	 |	  		(										|	  $				|
List			|	F	 |	  		(										|   (, $, ), number	|
Expression		|	F	 | plus, minus,	 times, negate, sum, product, mean	|         )			|
Operand			|	F	 |			(, number								|     ), (, number	|
Operands		|	F	 |			(, number								|	   )			|
Operand_Two		|	T	 |			(, number								|	   )			|
Atom			|	F	 |			(, number								|     ), (, number	|

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
   
   void File(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   Lists();
	   }else{
		   oops("Expected: left parenthesis in File");
	   }
   }

   void Lists(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   List();
		   Lists_Two();
	   }else{
		   oops("Expected: left parenthesis in Lists");
	   }
   }
   
   void Lists_Two(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		   List();
		   Lists_Two();  
	   }else if(t.sym == sym.EOF){ //lamda
	   }else{
		   oops("Expected: left parenthesis or lambda in Lists_Two");
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
			   oops("Expected: right parenthesis in List"); 
		   }
	   }else{
		   oops("Expected: left parenthesis in List");
	   }
   }
   
   void Expression(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.plus || t.sym == sym.minus || t.sym == sym.times){
		   Scanner.advance();
		   Operand();
		   Operand();
	   }else if(t.sym == sym.negate){
		   Scanner.advance();
		   Operand();
	   }else if(t.sym == sym.sum || t.sym == sym.product || t.sym == sym.mean){
		   Scanner.advance();
		   Operands();
	   }else{
		   oops("Error in Expression");
	   }
   }
   
   void Operand(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.number || t.sym == sym.lparen){
		   Atom();
	   }else{
		   oops("Expected: left parenthesis or number in Operand");
	   }
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
   
   void Atom(){
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.lparen){
		  List();
	   }else if (t.sym == sym.number){
		   Scanner.advance();
   	   }else{
		   oops("Expected: left parenthesis or number in Operand");
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
