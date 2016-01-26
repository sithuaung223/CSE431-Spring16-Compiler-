package lab3;

public class Token {

   public final static int
      Blank     =  0,
      Define    =  1,
      Semi      =  2,
      Or        =  3,
      Comma     =  4,
      Str       =  5,
      Terminal  =  6,
      Non       =  7,
      Start     =  8,
      With      =  9,
      Other     = 10;

   private static String[] nice;

   static {
      nice = new String[11];
      nice[Blank   ] = "Blank";
      nice[Define  ] = "Define";
      nice[Semi    ] = ";";
      nice[Or      ] = "|";
      nice[Comma   ] = ",";
      nice[Str     ] = "Str";
      nice[Terminal] = "Terminal";
      nice[Non     ] = "Non";
      nice[Start   ] = "Start";
      nice[With    ] = "With";
      nice[Other   ] = "Other";
   }

   int tokentype;
   String info;

   public Token(int tokentype) {
      this(tokentype, "");
   }

   public Token(int tokentype, String info) {
      this.tokentype   = tokentype;
      this.info        = info;
   }

   public final int type() {
      return(tokentype);
   }

   public final String strValue() {
      return(info);
   }

   public String toString() {
      return("Token " + 
           "<" + nice[tokentype] + "> " + 
           "\"" + info + "\""
            );
   }
}
