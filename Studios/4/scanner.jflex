package autogen;
import common.Listing;
import java_cup.runtime.*;


%%
%cup
%public

%line
%char
%eofval{
  return makeSymbol(sym.EOF);
%eofval}



%{
  private void echo() {
	Listing.get().echo(yytext());
  }
  private Symbol makeSymbol(int nSymType) {
    return makeSymbol(nSymType, yytext());
  }

  private Symbol makeSymbol(int nSymType, Object secondarg) {
    echo();
    return new Symbol(nSymType, yychar, yychar + yytext().length() - 1,
                        secondarg);
  }
%}


ALPHA=[A-Za-z]
DIGIT=[0-9]
BLANKS=[\ \t\b\015]
NEWLINE=[\n]

%%
"(*"([^)*]|")"|"*""*"*[^)*])*"*""*"*")"
{
	Listing.get().echo(yytext());
}
{NEWLINE}
{
	Listing.get().NewLine(1);
}
{BLANKS}+  
{ 
	Listing.get().echo(yytext());
}
"x"
{
	return makeSymbol(sym.x);
}
","
{
	return makeSymbol(sym.comma);
}
"a"
{
	return makeSymbol(sym.d, new Integer(10));
}
"b"
{
	return makeSymbol(sym.d, new Integer(11));
}
"c"
{
	return makeSymbol(sym.d, new Integer(12));
}
"d"
{
	return makeSymbol(sym.d, new Integer(13));
}
"e"
{
	return makeSymbol(sym.d, new Integer(14));
}
"f"
{
	return makeSymbol(sym.d, new Integer(15));
}
{DIGIT} 
{
	return(
          makeSymbol(sym.d, new Integer(Integer.parseInt(yytext())))
          );
}
.
{
	Listing.get().echo(yytext());
	Listing.get().oops("bad input");
}
