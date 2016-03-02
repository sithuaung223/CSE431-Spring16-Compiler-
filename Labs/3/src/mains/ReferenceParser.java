package mains;
import coursesolutions.courseparser.*;
import lab3.*;
import lab7.AbstractNode;
import lab7.PrintTree;
import common.Listing;
import common.OpenFile;

public class ReferenceParser {
   public static void main(String args[]) throws Exception {
      new Listing(System.out);
      CourseParser parser = new CourseParser(new CourseScanner(args.length == 0 ? new OpenFile("") : new OpenFile(args[0])));
      AbstractNode root = (AbstractNode) parser.parse().value;
      root.walkTree(new PrintTree(System.out));
   }

}
