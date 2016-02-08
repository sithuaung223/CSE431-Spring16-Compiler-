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
	
      //
      // FIXME:  Eliminate the loop below and instead call
      //   the method associated with your grammar's goal symbol
      //
      //  So, eliminate from here....
      Symbol t = Scanner.peek();
      while (t.sym != sym.EOF) {
         Listing.get().EmitMessage("Next token will be " + t);
         Scanner.advance();
         Listing.get().EmitMessage("Next2 token will be " + t);
         t = Scanner.peek();
      }
      //  ... to here
      
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
