package mains;
import autogen.*;
import common.Listing;
import common.OpenFile;

public class YourParser {
   public static void main(String args[]) throws Exception {
      new Listing(System.out);
      Parser parser = new Parser(new Yylex(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
      parser.parse();
   }

}
