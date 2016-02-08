package lab2;
import common.Listing;
import common.OpenFile;

public class tdn {

   public static void main (String args[]) {

      if (args.length != 1) throw new Error("Run tdn from ant, using target \"tdn\"");
      new Listing(System.out, "Add Haque Calculator");

      Scanner.setName(args[0]);
      RecursiveDescent p = new RecursiveDescent();
   }
}
