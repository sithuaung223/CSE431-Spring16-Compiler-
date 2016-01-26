package autogen;
import lab3.Token;
%%
%class Scanner
%type Token
%public
%line
%char

ALPHA=[A-Za-z]
DIGIT=[0-9]

WHITE_SPACE_CHAR=[\t\r\n ]

%%
{WHITE_SPACE_CHAR}+  
{ 
	return(new Token(Token.Blank));
}
"::="
{
	return(new Token(Token.Define, "::="));
}
";"
{
	return(new Token(Token.Semi, ";"));
}
"|"
{
	return(new Token(Token.Or, "|"));
}
","
{
	return(new Token(Token.Comma, ","));
}
"terminal"
{
	return(
          new Token(Token.Terminal, "terminal")
              );
}
"non"
{
	return(
          new Token(Token.Non, "non")
              );
}
"start"
{
	return(
          new Token(Token.Start, "start")
              );
}
"with"
{
	return(
          new Token(Token.With, "with")
              );
}
{ALPHA}({ALPHA}|{DIGIT})* 
{
	return(
          new Token(Token.Str, yytext())
              );
}
.
{
	return(
          new Token(Token.Other, yytext())
              );
}
