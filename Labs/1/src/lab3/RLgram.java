package lab3;
import java.util.Enumeration;

public class RLgram {

   public static void main (String args[]) {

      if (args.length != 1) throw new Error("Usage:  java rlgram file-or-URL");

      Enumeration e = new TokenEnum(args[0], false);
      Fsa fsa = new Fsa(e);
   }
}
