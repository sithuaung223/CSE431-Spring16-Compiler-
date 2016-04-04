package studio8;
import lab7.AbstractNode;
import lab8.*;
public class TestInfo implements SymInfo {
	public int reg;
	public TestInfo() {
	}
	public TestInfo(int reg) {
		this.reg = reg;
	}
	public AbstractNode getDefiningNode() { return null; }
	public TypeAttrs getType() { return null; }
	public ModsAttrs getMods() { return null; }
	public int getRegister() { return reg; }
	public void setRegister(int reg) { this.reg = reg; }

}
