package lab8;
import lab7.*;

/** A cast node */
public class CastNode extends AbstractNode implements AssignTypeRequiring {
   public AbstractNode getAssignTypeNode() { return getChild(); }
   public AbstractNode getSubjectNode() { return getChild().getSib(); }
}
