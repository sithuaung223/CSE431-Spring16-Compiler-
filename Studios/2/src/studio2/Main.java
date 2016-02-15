package studio2;
import common.Listing;
import common.OpenFile;

import autogen.*;

// $Id: Main.java 26 2010-05-08 18:06:55Z cytron $

/**
  * Studio 2.
  *
  * People in this group...
  *  Name:
  *  Name:
  *  Name:
  *
  */
public class Main {

   public static void main (String args[]) {
      new Listing(System.out, "Studio 2");

      // I did this one for you

      new Example().run();
 
      // Part 1, uncomment below as you go

      // new MatchingParens().run();
      // new Problem1a().run();
      // new Problem1d().run();

      // Part 2, uncomment below as you go

      // new Declarations().run();
      // new IfThenElseFi().run();

      // Part 3, uncomment below when you get here
      // The parser for this is done, see the studio instructions
      // new Expressions().run();

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
      S  ->  lparen S rparen        ?
          |  x                      ?
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
   }

}

/**                   Predicted by:
   S ->  A B c            ?
   A ->  a                ?
      |  lambda           ?
   B ->  b                ?
      |  lambda           ?
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
	   E();
   }
   public void E() {
	   if (peek() == sym.num) {
		   T();
		   EPrime();
	   }
	   else oops("error");
   }
   public void EPrime() {
	   if (peek() == sym.plus) {
		   expect(sym.plus);
		   T();
		   EPrime();
	   }
	   else if (peek() == sym.EOF) return;
	   else oops("error");
   }
   public void T() {
	   if (peek() == sym.num) {
		   expect(sym.num);
		   TPrime();
	   }
	   else oops("error");
   }
   public void TPrime() {
	   if (peek() == sym.times) {
		   expect(sym.times);
		   expect(sym.num);
		   TPrime();
	   }
	   else if (peek() == sym.EOF || peek() == sym.plus) return;
	   else oops("error");
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
