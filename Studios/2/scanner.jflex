package autogen;
import common.Listing;
import java_cup.runtime.*;

/* $Id: scanner.jflex 23 2010-05-08 15:49:37Z cytron $ */


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
","
{
	return(makeSymbol(sym.comma));
}
";"
{
	return(makeSymbol(sym.semi));
}
"("
{
	return(makeSymbol(sym.lparen));
}
")"
{
	return(makeSymbol(sym.rparen));
}
"+"
{
	return(makeSymbol(sym.plus));
}
"-"
{
	return(makeSymbol(sym.minus));
}
"*"
{
	return(makeSymbol(sym.times));
}
"/"
{
	return(makeSymbol(sym.divide));
}
"x"
{
	return(makeSymbol(sym.x));
}
"y"
{
	return(makeSymbol(sym.y));
}
"a"
{
	return(makeSymbol(sym.a));
}
"b"
{
	return(makeSymbol(sym.b));
}
"c"
{
	return(makeSymbol(sym.c));
}
"d"
{
	return(makeSymbol(sym.d));
}
"e"
{
	return(makeSymbol(sym.e));
}
"o"
{
	return(makeSymbol(sym.o));
}
"p"
{
	return(makeSymbol(sym.p));
}
"q"
{
	return(makeSymbol(sym.q));
}
"if"
{
	return(makeSymbol(sym.IF));
}
"else"
{
	return(makeSymbol(sym.ELSE));
}
"fi"
{
	return(makeSymbol(sym.FI));
}
"int"
{
	return(makeSymbol(sym.INT));
}
"float"
{
	return(makeSymbol(sym.FLOAT));
}
{ALPHA}({ALPHA}|{DIGIT})*
{
	return makeSymbol(sym.id);
}
{DIGIT}({DIGIT})* 
{
	return(
          makeSymbol(sym.num, new Integer(Integer.parseInt(yytext())))
              );
}
.
{
	Listing.get().echo(yytext());
	Listing.get().oops("bad input");
}
