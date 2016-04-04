package studio8;
import lab8.*;

public class CorrectnessChecks implements Runnable {
	private GensSymtab gs;
	public CorrectnessChecks(GensSymtab gs) {
		this.gs = gs;
	}
	private void oops(String s) {
		throw new Error("aborting because: " + s);
	}
	private void check(boolean b, String s) {
		if (!b) oops(s);
	}
	private int checkLevel(SymtabInterface sti) {
		check(sti.getCurrentNestLevel() >= 0, "Negative level");
		return sti.getCurrentNestLevel();
	}
	public void run() {
		System.out.println("Starting correctness tests");
		SymtabInterface sti = gs.genSymtab();
		int level = checkLevel(sti);
		sti.incrNestLevel();
		check(sti.getCurrentNestLevel() == level+1, "incremented level did not go up by 1");
		TestInfo ti1 = new TestInfo(5);
		sti.enter("xyzzy", ti1);
		sti.incrNestLevel();
		check(sti.lookup("xyzzy") == ti1, "Did not find right symbol info");
		TestInfo ti2 = new TestInfo(6);
		sti.enter("xyzzy", ti2);
		check(sti.lookup("xyzzy") == ti2, "Nested symbol information not returned");
		sti.decrNestLevel();
		check(sti.lookup("xyzzy") == ti1, "Should have gotten back told sym info");
		System.out.println("End correctness tests");
		check(sti.lookup("notpresent")==null, "Failed to not find a nonexistent symbol");

		// push a whole bunch of symbols
		sti.incrNestLevel();
		for (int i=0; i < 1000; ++i) sti.enter("sym"+i, ti1);
		// are they all there?
		for (int i=0; i < 1000; ++i) check(sti.lookup("sym"+i)!= null, "missing sym");
		// now pop that level and they should all be gone
		sti.decrNestLevel();
		for (int i=0; i < 1000; ++i) check(sti.lookup("sym"+i)== null, "sym should be gone");
		
		// Try many symbols, one at each nest level
		for (int i=1; i <= 70; ++i) {
			sti.incrNestLevel();
			sti.enter("sym" + i, new TestInfo(i));
		}
		for (int i=70; i >=1; --i) {
			check(
					((TestInfo)(sti.lookup("sym"+i))).reg == i,
					"bad symbol info");
		}
		for (int i=70; i >=2; --i) {
			sti.decrNestLevel();
			check(sti.lookup("sym"+i)==null,"sym should be gone");
			check(
					((TestInfo)(sti.lookup("sym"+(i-1)))).reg == i-1,
					"bad symbol info");
		}
		
		
	}

}
