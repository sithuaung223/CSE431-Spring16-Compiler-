package lab3;

import java.util.Enumeration;
import java.io.DataInputStream;
import common.OpenFile;
import autogen.Scanner;

public class TokenEnum implements Enumeration {
      private Scanner scanner;
      private Token next;
      private boolean debug;
      public TokenEnum(String f, boolean debug) {
         this.debug = debug;
         DataInputStream dis = new OpenFile(f);
         scanner =  new Scanner(dis);
         advance();
      }
      public void setDebug(boolean val) { this.debug = val; }
      private void advance() {
         try {
            next = scanner.yylex();
         } catch (java.io.IOException e) {
            next = null;
         }
      }
      public boolean hasMoreElements() {
         return(next != null);
      }
      public Object nextElement() {
         Object ans = next;
         if (debug) System.out.println("TokenEnum returning " + ans);
         advance();
         return(ans);
      }
}
