package studio4;
import java_cup.runtime.*;
import common.Listing;
import common.OpenFile;
import autogen.*;

// $Id: Main.java 17 2010-05-09 22:52:42Z cytron $

public class Main {
	public static void main(String args[]) throws Exception {
	      new Listing(System.out, "Studio 7");

	      new Problem1a().run();  // I did this one for you
	      new Problem1b().run();
	      new Problem2().run();
	      new Problem3().run();
	      new Problem4().run();
	 	}

}

class Problem1a extends BottomUpParse {
	public lr_parser getParser(Scanner s) { return new Parsercup1a(s); }
	public String getProblemName() { return "Problem 1a"; }
	public String[] getTestFiles() {
		return new String[] {
				"TestFiles/cup1.good1", 
				"TestFiles/cup1.good2"
		};
	}

}
class Problem1b extends BottomUpParse {
	public lr_parser getParser(Scanner s) { return new Parsercup1b(s); }
	public String getProblemName() { return "Problem 1b"; }
	public String[] getTestFiles() {
		return new String[] {
				"TestFiles/cup1.good1", 
				"TestFiles/cup1.good2"
		};
	}

}
class Problem2 extends BottomUpParse {
	public lr_parser getParser(Scanner s) { return new Parsercup2(s); }
	public String getProblemName() { return "Problem 2"; }
	public String[] getTestFiles() {
		return new String[] {
				"TestFiles/cup1.good1",  // Same testfiles as Problem 1
				"TestFiles/cup1.good2"
		};
	}

}
class Problem3 extends BottomUpParse {
	public lr_parser getParser(Scanner s) { return new Parsercup3(s); }
	public String getProblemName() { return "Problem 3"; }
	public String[] getTestFiles() {
		return new String[] {
				"TestFiles/cup3.good1", 
				"TestFiles/cup3.good2",
				"TestFiles/cup3.bad"
		};
	}

}
class Problem4 extends BottomUpParse {
	public lr_parser getParser(Scanner s) { return new Parsercup4(s); }
	public String getProblemName() { return "Problem 4"; }
	public String[] getTestFiles() {
		return new String[] {
				"TestFiles/cup4.good", 
				"TestFiles/cup4.bad"
		};
	}

}
