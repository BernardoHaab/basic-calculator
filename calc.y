
%{
  import java.io.*;
  import java.util.HashMap;
  import java.util.Stack;
%}

%token NL          /* newline  */
%token <dval> NUM  /* a number */
%token IF, WHILE, ELSE, PRINT, FOR, DEF
%token <sval> IDENT

%type <obj> exp, cmd, line, input, lcmd, lparams, lexp

%nonassoc '='
%nonassoc '<'
%left '-' '+'
%left '*', '/'
%left NEG          /* negation--unary minus */
%right '^'         /* exponentiation        */

%%

input:   /* empty string */ {$$=null;}
       | input line  { if ($2 != null) {
                           System.out.print("Avaliacao: " + ((INodo) $2).avalia() +"\n> ");
							$$=$2;
						}
					    else {
                          System.out.print("\n> ");
						  $$=null;
						}
					}
       ;

line:    NL      { if (interactive) System.out.print("\n> "); $$ = null; }
       | exp NL  { $$ = $1;
		   System.out.println("\n= " + $1);
                   if (interactive) System.out.print("\n>: "); }
       | cmd NL
       ;

cmd :  exp ';'                                 { $$ = $1; }
    |  IF '(' exp ')' cmd                      { $$ = new NodoNT(TipoOperacao.IF,(INodo)$3, (INodo)$5, null); }
    |  IF '(' exp ')' cmd ELSE cmd             { $$ = new NodoNT(TipoOperacao.IFELSE,(INodo)$3, (INodo)$5, (INodo)$7); }
    |  WHILE '(' exp ')' cmd                   { $$ = new NodoNT(TipoOperacao.WHILE,(INodo)$3, (INodo)$5, null); }
    |  FOR '(' exp ';' exp ';' exp ')' cmd     { $$ = new NodoNT(TipoOperacao.FOR, (INodo)$3, (INodo)$5, (INodo)$7, (INodo)$9); }
    |  DEF IDENT '(' lparams ')' cmd           { $$ = new NodoNT(TipoOperacao.FUNC_DEF, $2, (INodo)$4, (INodo)$6); }
    | '{' lcmd '}'                             { $$ = $2; }
    | error ';'                                { $$ = new NodoNT(); }
    ;

lcmd : lcmd cmd                 { $$ = new NodoNT(TipoOperacao.SEQ,(INodo)$1,(INodo)$2); }
     |                          { $$ = new NodoNT(); }
     ;

lexp : lexp ',' exp                 { $$ = new NodoNT(TipoOperacao.SEQ,(INodo)$1,(INodo)$3); }
     | exp                          { $$ = new NodoNT(TipoOperacao.SEQ,(INodo)$1,null); }
     |                              { $$ = new NodoNT(); }
     ;

lparams : lparams ',' IDENT       { $$ = new NodoNT((INodo)$1,$3); }
        | IDENT                 { $$ = new NodoID($1); }
        |                       { $$ = new NodoNT(); }
        ;

exp:     NUM                { $$ = new NodoTDouble($1); }
       | IDENT '=' exp            { $$ = new NodoNT(TipoOperacao.ATRIB, $1, (INodo)$3); }
       | IDENT              { $$ = new NodoID($1); }
       | exp '+' exp        { $$ = new NodoNT(TipoOperacao.ADD,(INodo)$1,(INodo)$3); }
       | exp '-' exp        { $$ = new NodoNT(TipoOperacao.SUB,(INodo)$1,(INodo)$3); }
       | exp '*' exp        { $$ = new NodoNT(TipoOperacao.MULL,(INodo)$1,(INodo)$3); }
       | exp '/' exp        { $$ = new NodoNT(TipoOperacao.DIV,(INodo)$1,(INodo)$3); }
       | exp '<' exp        { $$ = new NodoNT(TipoOperacao.LESS,(INodo)$1,(INodo)$3); }
       | '-' exp  %prec NEG { $$ = new NodoNT(TipoOperacao.UMINUS,(INodo)$2,null); }
       | exp '^' exp        { $$ = new NodoNT(TipoOperacao.POW,(INodo)$1,(INodo)$3); }
       | '(' exp ')'        { $$ = $2; }
       |  IDENT '('lexp')'  { $$ = new NodoNT(TipoOperacao.FUNC_CALL,$1,(INodo)$3); }
       ;

%%

  public static HashMap<String, ResultValue> memory = new HashMap<>();
  public static HashMap<String, SymbolTable> contextTable = new HashMap<>();
  public static Stack<String> contextStack = new Stack();
  private Yylex lexer;


  private int yylex () {
    contextStack.add("global");
    int yyl_return = -1;
    try {
      yylval = new ParserVal(0);
      yyl_return = lexer.yylex();
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_return;
  }


  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  public Parser(Reader r) {
    lexer = new Yylex(r, this);
  }


  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("BYACC/Java with JFlex Calculator Demo");

    Parser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new Parser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
	    yyparser = new Parser(new InputStreamReader(System.in));
    }

    yyparser.yyparse();

    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }
