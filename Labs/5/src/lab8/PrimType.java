package lab8;

import lab7.*;

public class PrimType extends TypeBridge {
      public String pname;

      public PrimType (String pname) {
         this.pname = pname.trim().intern();
      }

      public int hashCode() {
         return(pname.hashCode());
      }

      public boolean isPrimitive() { return true; }

      public boolean equals(Object o) {
         boolean ans = true;
         if (! (o instanceof PrimType)) ans = false;
         else {
            PrimType pt = (PrimType)o;
            if (! (this.pname.equals(pt.pname))) ans = false;
         }
         return(ans);
      }

      public String getTypeName() { return(pname); }
      public String getTypeString()  { return(pname.substring(0,1));}
}

   
