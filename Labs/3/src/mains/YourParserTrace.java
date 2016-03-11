package mains;
import autogen.*;
import common.Listing;
import common.OpenFile;

public class YourParserTrace {
   public static void main(String args[]) throws Exception {
      if (args.length < 1) throw new Error("Usage:  java main file-or-ULR");

      new Listing(System.out);
      Parser parser = new Parser(new Yylex(new OpenFile(args[0])));
      parser.debug_parse();
   }

}
