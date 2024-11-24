%%

%byaccj

%{
  private Parser yyparser;

  public Yylex(java.io.Reader r, Parser yyparser) {
    this(r);
    this.yyparser = yyparser;
  }
%}

NUM = [:digit:]+ ("." [:digit:]+)?
IDENT = [:letter:]+ ([:letter:]|[:digit:])*
NL  = \n | \r | \r\n

%%

/* operators */
"," |
";" |
"{" |
"}" |
"+" |
"-" |
"*" |
"/" |
"^" |
"(" |
")" |
"<" |
">" |
"="   { return (int) yycharat(0); }

"<=" { return Parser.LTE; }
">=" { return Parser.GTE; }
"==" { return Parser.EQ; }
"!=" { return Parser.NOTEQ; }
"+=" { return Parser.ASSPLUS; }
"*=" { return Parser.ASSMULTI; }

"&&" { return Parser.AND; }
"||" { return Parser.OR; }
"!" { return Parser.NOT; }

if  { return Parser.IF; }
else  { return Parser.ELSE; }
while  { return Parser.WHILE; }
print  { return Parser.PRINT; }
for  { return Parser.FOR; }
define { return Parser.DEF; }
return { return Parser.RETURN; }


{NL}   { return Parser.NL; }

{NUM}  { yyparser.yylval = new ParserVal(Double.parseDouble(yytext()));
         return Parser.NUM; }

{IDENT} { yyparser.yylval = new ParserVal(yytext());
         return Parser.IDENT; }

[ \t]+ { }

[^]    { System.err.println("Error: unexpected character '"+yytext()+"'"); return -1; }
