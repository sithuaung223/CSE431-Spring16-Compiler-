package studio2;
import java_cup.runtime.Symbol;
import common.Listing;
import common.OpenFile;
import autogen.*;

// $Id: Main.java 26 2010-05-08 18:06:55Z cytron $

/**
  * Studio 2.
  *
  * People in this group...
  *  Name: Evan Schwartzman
  *  Name: Si Thu Aung
  *  Name: Ethan Vaughan
  *
  */
public class Main {

   public static void main (String args[]) {
      new Listing(System.out, "Studio 2");

      // I did this one for you

      new Example().run();
 
      // Part 1, uncomment below as you go

      //new MatchingParens().run();
      //new Problem1a().run();
      // new Problem1d().run();

      // Part 2, uncomment below as you go

      // new Declarations().run();
      // new IfThenElseFi().run();

      // Part 3, uncomment below when you get here
      // The parser for this is done, see the studio instructions
       new Expressions().run();

      // Part 4, 20 minutes, leave this uncommented, just work on the grammar
      // new IfThenElse().run();
   }

}

/** I've done this one completely for you.  
  *    You're welcome :-)
  */
class Example extends RecursiveDescent {

   /* Original grammar     Predicted by:
       S ->  a B c             a
       B ->  b                 b
          |  d                 d
    * Fine for recursive descent parsing
    */

   public String getProblemName() { return "Example"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/example.good1", 
         "TestFiles/example.good2",
         "TestFiles/example.bad"
      };
   }

   public void S() {
      if (peek() == sym.a) { // predicts S -> a B c
         expect(sym.a);
         B();
         expect(sym.c);
      }
      else oops("expected a");
   }

   public void B() {
      if (peek() == sym.b) {      // predicts B -> b
         expect(sym.b);
      }
      else if (peek() == sym.d) { // predicts B -> d
         expect(sym.d);
      }
      else oops("expected b or d");
   }

}

/** Fill in the "Predicted by" column and finish writing the parser
  *    by completing the S() method and writing other methods as needed
  *
  *                              Predicted by:
      S  ->  lparen S rparen        lparen
          |  x                      x
  */

class MatchingParens extends RecursiveDescent {

   public String getProblemName() { return "Matching Parentheses"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/parens.good1", 
         "TestFiles/parens.good2", 
         "TestFiles/parens.bad" 
      };
   }

   /** Fill me in */
   public void S() {
	   if (Scanner.peek().sym == sym.lparen){
		   Scanner.advance();
		   S();
		   if (Scanner.peek().sym == sym.rparen){
			   Scanner.advance();
		   }
	   }else if (Scanner.peek().sym == sym.x){
		   Scanner.advance();
	   }else{
		   oops("fail");
	   }
   }

}

/**                   Predicted by:
   S ->  A B c            a,b,c
   A ->  a                a
      |  lambda           b,c
   B ->  b                b
      |  lambda           c
 */

class Problem1a extends RecursiveDescent {

   public String getProblemName() { return "Problem 1a page 137"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/prob1a.good1", 
         "TestFiles/prob1a.good2", 
         "TestFiles/prob1a.good3", 
         "TestFiles/prob1a.bad" 
      };
   }

   /** Fill me in */
   public void S() {
	   A();
	   B();
	   expect(sym.c);
   }
   
   public void A() {
	   if (Scanner.peek().sym == sym.a){
		   Scanner.advance();
	   } else if (Scanner.peek().sym == sym.b || Scanner.peek().sym == sym.c){
	   }else{
		   oops("no");
	   }
   }
   
   public void B() {
	   if (Scanner.peek().sym == sym.b){
		   Scanner.advance();
	   } else if (Scanner.peek().sym == sym.c){
		   
	   }else{
		   oops("no");
	   }
   }

}

/**                 Predicted by:
   S ->  a S e          ?
      |  B              ?
   B ->  b B e          ?
      |  C              ?
   C ->  c C e          ?
      |  d              ?
 */

class Problem1d extends RecursiveDescent {

   public String getProblemName() { return "Problem 1d page 137"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/prob1d.good1", 
         "TestFiles/prob1d.good2", 
         "TestFiles/prob1d.good3", 
         "TestFiles/prob1d.bad" 
      };
   }

   /** Fill me in */
   public void S() {
   }

}

/** You will have to get rid of the left recursion in this one.
  *     Fix the grammar as you need to below and supply the prediction sets
  *      Don't just hack the grammar to do what you think it should
  *      Use the transformations taught in class to be sure it's right
  *

   Original grammar:
      S    ->  Dcls
      Dcls ->  Dcls Dcl
            |  Dcl
      Dcl  ->  Type Vars semi
      Vars ->  Vars comma id
            |  id
      Type ->  INT
            |  FLOAT

  *  Your transformed grammar and its predict sets:
  *  
  *   S    ->  Dcls						int, float
      Dcls ->  Dcl More_Dcls			int, float
      More_Dcls-> Dcl More_Dcls			int, float
            |  lamda					eof
      Dcl  ->  Type Vars semi			int, float
      Vars -> Var1 Var2 				id
      Var1 -> id						id
      Var2 -> comma id Var2				comma
      		| lamda						semi
      Type ->  INT						int
            |  FLOAT					float
  */

class Declarations extends RecursiveDescent {

   public String getProblemName() { return "Declarations"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/dcls.good", 
         "TestFiles/dcls.bad1", 
         "TestFiles/dcls.bad2" 
      };
   }

   /** Fill me in */
   public void S() {
   }

}

/** You will have to do some things to this to make it work--figure it out.
  *   FYI "fi" is the keyword some languages use to end an "if" statement
  *

   Original grammar:
      S    ->  if e S else S fi
            |  if e S fi
            |  y

  *  Your transformed grammar and its predict sets:

  */

class IfThenElseFi extends RecursiveDescent {

   public String getProblemName() { return "If then else with \"fi\""; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/ifthenelsefi.good1", 
         "TestFiles/ifthenelsefi.good2", 
         "TestFiles/ifthenelsefi.good3", 
         "TestFiles/ifthenelsefi.good4"
      };
   }

   /** Fill me in */
   public void S() {
   }

}

/** You will have to do some things to this to make it work--figure it out.
  *   Also, when done with the parsing part of it, see the studio 
  *   instructions, as there is more work to do.
  *

   Original grammar:
         E ->  E plus T
            |  T
         T ->  T times num
            |  num

  *  Your transformed grammar and its predict sets:
      E      -> T EPrime                     num
      EPrime -> plus T EPrime                plus
              | lambda                       eof
      T      -> num TPrime                   num
      TPrime -> times num TPrime             times
              | lambda                       plus, eof


  */

class Expressions extends RecursiveDescent {

   public String getProblemName() { return "Arithmetic Expressions"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/exprs.good1", 
         "TestFiles/exprs.good2", 
         "TestFiles/exprs.good3",
         "TestFiles/exprs.bad"
      };
   }

   /** Fill me in */
   public void S() {
	   System.out.println( "answer: " +E() );
   }
   public int E() {
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.num) {
		   int u  = T(0);
		   int y = EPrime(u);
		   return y;
	   }
	   return 0;
   }
   public int EPrime(int c) {
	   if (peek() == sym.plus) {
		   expect(sym.plus);
		   int p = c+T(c);
		   int n = EPrime(p);
		   return n;
	   }
	   else if (peek() == sym.EOF) {};
	   return c;
   }
   public int T(int b) {
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.num) {
		   expect(sym.num);
		   return TPrime((int) t.value);
	   }
	   return b;
   }
   public int TPrime(int a) {
	   Symbol t = Scanner.peek();
	   if (t.sym == sym.times) {
		   expect(sym.times);
		   Symbol m = Scanner.peek();
		   expect(sym.num);
		   return TPrime(a* ( (int) m.value));
	   }
	   else if (peek() == sym.EOF || peek() == sym.plus) {};
	   return a;
   }

}


/** If you make it this far, just see if you can get the
  *   grammar into a form where it's Top-Down parseable
  *   Don't worry about implementing the parser.
  *

   Original grammar:
      S    ->  if e S else S
            |  if e S
            |  y

  *  Your transformed grammar and its predict sets:

  */

class IfThenElse extends RecursiveDescent {

   public String getProblemName() { return "Standard if then else"; }
   public String[] getTestFiles() {
      return new String[] {
         "TestFiles/ifthenelse.good1", 
         "TestFiles/ifthenelse.good2", 
         "TestFiles/ifthenelse.bad" 
      };
   }

   /** Fill me in */
   public void S() {
   }

}
