package autogen;
import common.Listing;
import java_cup.runtime.*;

// $Id: scanner.jflex 48 2010-05-23 21:25:04Z cytron $


%%
%cup
%public

%line
%char
%eofval{
  return makeSymbol(sym.EOF);
%eofval}



%{
  private Symbol makeSymbol(int nSymType) {
    return new Symbol(nSymType, yychar, yychar + yytext().length() - 1);
  }

  private Symbol makeSymbol(int nSymType, Object secondarg) {
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
"("
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.lparen));
}
")"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.rparen));
}
"sum"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.sum));
}
"product"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.product));
}
"negate"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.negate));
}
"plus"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.plus));
}
"minus"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.minus));
}
"times"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.times));
}
"mean"
{
	Listing.get().echo(yytext());
	return(makeSymbol(sym.mean));
}
{DIGIT}({DIGIT})* 
{
	Listing.get().echo(yytext());
	return(
          makeSymbol(sym.number, new Integer(Integer.parseInt(yytext())))
              );
}
.
{
	Listing.get().echo(yytext());
	Listing.get().oops("bad input");
}
