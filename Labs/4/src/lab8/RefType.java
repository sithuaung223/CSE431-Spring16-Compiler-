package lab8;
import java.util.Vector;

import lab7.*;

import java.util.Enumeration;
import java.util.StringTokenizer;

public class RefType extends TypeBridge {
      public Vector rname;

      public RefType (Vector rname) {
         this.rname = rname;
      }

      public RefType(String s) {
         this(encode(s));
      }

      private static Vector encode(String s) {
         Vector ans = new Vector();
         Enumeration e = new StringTokenizer(s, ".");
         while (e.hasMoreElements()) ans.addElement(e.nextElement());
         return ans;
      }

      public int hashCode() {
         int ans = 0;
         for (int i=0; i < rname.size(); ++i) {
           ans += rname.elementAt(i).hashCode();
         }
         return(ans);
      }


      public boolean isPrimitive() { return false; }
      public String getTypeString()  {
         return ("L"+getClassSig()+";");
      }

      public String getClassSig() {
         String mid="";
         Enumeration e = rname.elements();
         while (e.hasMoreElements()) {
            if (mid.length() > 0) mid += "/";
            mid += (String)e.nextElement();
         }
         return(mid);
      }

      public String getTypeName() { return(rname.toString()); }

      public boolean equals(Object ov) {
         boolean ans = true;
         if (! (ov instanceof RefType)) return(false);
         RefType rt = (RefType)ov;
         if (this.rname.size() != rt.rname.size()) return(false);
         for (int i=0; i < this.rname.size(); ++i) {
            if (! this.rname.elementAt(i).equals(rt.rname.elementAt(i)))
               return(false);
         }
         return(ans);
      }

}

