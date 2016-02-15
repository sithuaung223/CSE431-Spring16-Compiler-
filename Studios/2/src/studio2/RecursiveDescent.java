package studio2;
import common.Listing;

import java_cup.runtime.*;
import autogen.*;

// $Id: RecursiveDescent.java 28 2010-05-08 18:09:50Z cytron $

public abstract class RecursiveDescent {

   public RecursiveDescent() {
   }

   public void run() {
      String[] args = getTestFiles();
      for (int i=0; i < args.length; ++i) {
       try {
         Scanner.setName(args[i]);
         System.out.println("\nRunning on problem " + getProblemName());
         System.out.println("   test file: " + args[i]);
         Listing.get().NewLine(1);
         Scanner.init();
         
         S();
         expect(sym.EOF);
         
         System.out.println("\nSuccessful parse");
       } catch(Throwable t) {
         t.printStackTrace();
         System.out.println("\nAborting this test");
       }
      }

   }

   /** The goal symbol for the grammar, called to start off the parse */
   public abstract void     S();
   
   /** Provides an array of file names on which the parse will be run */
   public abstract String[] getTestFiles();
   
   /** Used for identification purpopses */
   public abstract String   getProblemName();

   /** Returns a peek at the next symbol.  The values returned
     * are defined in sym.java
     */
   protected int peek() {
      return Scanner.peek().sym;
   }

   /** Check the next symbol for what is expected.
     *  Stop with an error if something unexpected is seen
     *  Otherwise, advance the input.
     */
   protected void expect(int sym) {
      expect(sym, "Expected symbol #" +sym + " (as defined in sym.java)");
   }
   protected void expect(int sym, String message) {
      if (peek() != sym) oops(message);
      else Scanner.advance(); 
   }
      

   protected void oops(String s) {
      s = " Peeking at symbol #" + peek() + ", " + s;
      System.out.println();  System.out.flush();
      System.out.println("Error: " + s);
      throw new Error(s);
   }
}
