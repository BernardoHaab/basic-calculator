//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "calc.y"

  import java.io.*;
  import java.util.HashMap;
  import java.util.Stack;
//#line 22 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NL=257;
public final static short NUM=258;
public final static short IF=259;
public final static short WHILE=260;
public final static short ELSE=261;
public final static short PRINT=262;
public final static short FOR=263;
public final static short DEF=264;
public final static short RETURN=265;
public final static short IDENT=266;
public final static short NEG=267;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    2,    2,    4,    4,    7,    7,    7,    5,    6,
    6,    6,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    8,    8,    8,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    2,    2,    5,    7,    5,    9,
    8,    3,    2,    2,    0,    3,    1,    0,    1,    3,
    1,    0,    1,    3,    1,    3,    3,    3,    3,    3,
    2,    3,    3,    4,    4,    1,    3,
};
final static short yydefred[] = {                         1,
    0,    0,    3,   23,    0,    0,    0,    0,    0,    0,
    0,   15,    0,    0,    2,   13,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    4,    0,    0,    0,    0,
    0,    0,    6,    5,    0,    0,    0,    0,    0,    0,
    0,   33,   12,    0,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   19,   21,    0,   34,    0,    0,
    9,    0,    0,    0,    0,    0,    0,    0,   20,    8,
    0,    0,    0,    0,    0,    0,    0,   11,   10,   37,
    0,   35,
};
final static short yydgoto[] = {                          1,
   44,   14,   15,   25,   56,   57,   41,   74,
};
final static short yysindex[] = {                         0,
  -29,  -57,    0,    0,  -28,   -7,   -2, -263,  -39,   -8,
   -8,    0,  -24, -249,    0,    0,   -8,   -8,   -8,    3,
   -8,   -8,  -81,  121,  -20,    0,   -8,   -8,   -8,   -8,
   -8,   -8,    0,    0,  148,  231,  127, -240,  303,  303,
  -37,    0,    0,  250,    0,   -3,  -32,  -32,  -81,  -81,
  -81,    1,    1,   -8,    0,    0,  -27,    0,   -8, -216,
    0,  257,  -71, -240,  303,    1,   -8,  -11,    0,    0,
  238,   -8, -206,  -62,    1,  276,   -8,    0,    0,    0,
  295,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    6,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -14,   13,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -13,  -35,   27,
    0,    0,    0,    0,    0,   20,   63,   84,   33,   54,
   74,    0,    0,    0,    0,    0,    0,    0,    0,  -40,
    0,    0,    0,    0,   40,    0,    0,    0,    0,    0,
    0,    0,  -58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
  129,  281,    0,    0,    5,    0,    0,    0,
};
final static int YYTABLESIZE=397;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          7,
   22,   16,   20,   58,    7,   24,   59,   34,   24,   30,
   11,   17,   32,   63,   31,   10,   64,   30,   29,   11,
   28,   21,   31,   24,   10,   55,   18,   22,   11,   18,
   22,   11,   18,   10,   33,   27,   10,   19,   30,   29,
   11,   28,   38,   31,   66,   10,   25,   25,   25,   25,
   25,   68,   25,   31,   31,   31,   31,   31,   77,   31,
   30,   32,   78,   30,   25,   25,   36,   17,   69,   32,
   17,   31,   31,   28,   28,   28,   28,   28,   30,   28,
   16,    0,    7,   16,    7,    0,    0,    0,    0,    0,
   32,   28,   28,   12,   29,   29,   29,   29,   29,   25,
   29,    0,   12,   27,   43,   27,   27,   27,    0,    0,
    0,   12,   29,   29,   32,   32,   32,   32,   32,    0,
   32,   27,   27,   12,   26,    0,   26,   26,   26,   13,
    0,    0,   32,   32,    0,    0,    0,    0,   23,   24,
    0,    0,   26,   26,    0,   35,   36,   37,    0,   39,
   40,    0,    0,    0,    0,   46,   47,   48,   49,   50,
   51,   42,   30,   29,    0,   28,    0,   31,   30,   29,
    0,   28,    0,   31,    0,    0,    0,    0,    0,    0,
   27,    0,   62,    0,    0,   54,   27,   65,   52,   30,
   29,    0,   28,    0,   31,   71,    0,    0,    0,    0,
   76,    0,    0,    0,    0,   81,    0,   27,    0,    0,
    0,    0,    0,    0,   32,    7,    7,    7,    7,    7,
   32,   24,    7,    7,    7,    7,    2,    3,    4,    5,
    6,    0,   26,    7,    8,    2,    9,    4,    5,    6,
    0,   32,    7,    8,    2,    9,    4,    5,    6,    4,
    0,    7,    8,   72,    9,    0,    2,    9,    4,    5,
    6,    0,   25,    7,    8,    0,    9,    0,    0,   31,
    0,   53,   30,   29,    0,   28,   30,   31,   75,   30,
   29,    0,   28,    0,   31,    0,    0,    0,    0,   28,
   27,   30,   29,    0,   28,    0,   31,   27,   30,   29,
    0,   28,    0,   31,    0,   45,    0,    0,   33,   27,
   29,    0,    0,    0,    0,   67,   27,   30,   29,   27,
   28,    0,   31,    0,   32,    0,    0,    0,    0,    0,
   32,   32,   60,   61,   80,   27,   30,   29,    0,   28,
   26,   31,    0,   32,   30,   29,   70,   28,   73,   31,
   32,    0,    0,   82,   27,   79,    0,    0,    0,    0,
    0,    0,   27,    0,    0,    0,    0,    0,    0,   32,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   32,    0,
    0,    0,    0,    0,    0,    0,   32,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   40,   59,  266,   41,   45,   41,   44,  257,   44,   42,
   40,   40,   94,   41,   47,   45,   44,   42,   43,   40,
   45,   61,   47,   59,   45,  266,   41,   41,   40,   44,
   44,   40,   40,   45,   59,   60,   45,   40,   42,   43,
   40,   45,   40,   47,  261,   45,   41,   42,   43,   44,
   45,  123,   47,   41,   42,   43,   44,   45,  265,   47,
   41,   94,  125,   44,   59,   60,  125,   41,   64,   94,
   44,   59,   60,   41,   42,   43,   44,   45,   59,   47,
   41,   -1,  123,   44,  125,   -1,   -1,   -1,   -1,   -1,
   94,   59,   60,  123,   41,   42,   43,   44,   45,   94,
   47,   -1,  123,   41,  125,   43,   44,   45,   -1,   -1,
   -1,  123,   59,   60,   41,   42,   43,   44,   45,   -1,
   47,   59,   60,  123,   41,   -1,   43,   44,   45,    1,
   -1,   -1,   59,   60,   -1,   -1,   -1,   -1,   10,   11,
   -1,   -1,   59,   60,   -1,   17,   18,   19,   -1,   21,
   22,   -1,   -1,   -1,   -1,   27,   28,   29,   30,   31,
   32,   41,   42,   43,   -1,   45,   -1,   47,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,   -1,   -1,
   60,   -1,   54,   -1,   -1,   59,   60,   59,   41,   42,
   43,   -1,   45,   -1,   47,   67,   -1,   -1,   -1,   -1,
   72,   -1,   -1,   -1,   -1,   77,   -1,   60,   -1,   -1,
   -1,   -1,   -1,   -1,   94,  256,  257,  258,  259,  260,
   94,  257,  263,  264,  265,  266,  256,  257,  258,  259,
  260,   -1,  257,  263,  264,  256,  266,  258,  259,  260,
   -1,   94,  263,  264,  256,  266,  258,  259,  260,  258,
   -1,  263,  264,  265,  266,   -1,  256,  266,  258,  259,
  260,   -1,  257,  263,  264,   -1,  266,   -1,   -1,  257,
   -1,   41,   42,   43,   -1,   45,  257,   47,   41,   42,
   43,   -1,   45,   -1,   47,   -1,   -1,   -1,   -1,  257,
   60,   42,   43,   -1,   45,   -1,   47,   60,   42,   43,
   -1,   45,   -1,   47,   -1,   25,   -1,   -1,   59,   60,
  257,   -1,   -1,   -1,   -1,   59,   60,   42,   43,  257,
   45,   -1,   47,   -1,   94,   -1,   -1,   -1,   -1,   -1,
  257,   94,   52,   53,   59,   60,   42,   43,   -1,   45,
  257,   47,   -1,   94,   42,   43,   66,   45,   68,   47,
   94,   -1,   -1,   59,   60,   75,   -1,   -1,   -1,   -1,
   -1,   -1,   60,   -1,   -1,   -1,   -1,   -1,   -1,   94,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   94,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   94,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=267;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"NL","NUM","IF","WHILE","ELSE","PRINT",
"FOR","DEF","RETURN","IDENT","NEG",
};
final static String yyrule[] = {
"$accept : input",
"input :",
"input : input line",
"line : NL",
"line : exp NL",
"line : cmd NL",
"cmd : exp ';'",
"cmd : IF '(' exp ')' cmd",
"cmd : IF '(' exp ')' cmd ELSE cmd",
"cmd : WHILE '(' exp ')' cmd",
"cmd : FOR '(' exp ';' exp ';' exp ')' cmd",
"cmd : DEF IDENT '(' lparams ')' '{' func_body '}'",
"cmd : '{' lcmd '}'",
"cmd : error ';'",
"lcmd : lcmd cmd",
"lcmd :",
"lexp : lexp ',' exp",
"lexp : exp",
"lexp :",
"param : IDENT",
"lparams : lparams ',' param",
"lparams : param",
"lparams :",
"exp : NUM",
"exp : IDENT '=' exp",
"exp : IDENT",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '<' exp",
"exp : '-' exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
"exp : IDENT '(' lexp ')'",
"func_body : cmd RETURN exp ';'",
"func_body : cmd",
"func_body : RETURN exp ';'",
};

//#line 89 "calc.y"


  public static HashMap<String, ResultValue> memory = new HashMap<>();
  public static HashMap<String, SymbolTable> contextTable = new HashMap<>();
  public static Stack<String> contextStack = new Stack<>();
  private Yylex lexer;


  private int yylex () {
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
    contextStack.add("global");
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
//#line 368 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 24 "calc.y"
{yyval.obj=null;}
break;
case 2:
//#line 25 "calc.y"
{ if (val_peek(0).obj != null) {
                           System.out.print("Avaliacao: " + ((INodo) val_peek(0).obj).avalia() +"\n> ");
							yyval.obj=val_peek(0).obj;
						}
					    else {
                          System.out.print("\n> ");
						  yyval.obj=null;
						}
					}
break;
case 3:
//#line 36 "calc.y"
{ if (interactive) System.out.print("\n> "); yyval.obj = null; }
break;
case 4:
//#line 37 "calc.y"
{ yyval.obj = val_peek(1).obj;
		   System.out.println("\n= " + val_peek(1).obj);
                   if (interactive) System.out.print("\n>: "); }
break;
case 6:
//#line 43 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 7:
//#line 44 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.IF,(INodo)val_peek(2).obj, (INodo)val_peek(0).obj, null); }
break;
case 8:
//#line 45 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.IFELSE,(INodo)val_peek(4).obj, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 9:
//#line 46 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.WHILE,(INodo)val_peek(2).obj, (INodo)val_peek(0).obj, null); }
break;
case 10:
//#line 47 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FOR, (INodo)val_peek(6).obj, (INodo)val_peek(4).obj, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 11:
//#line 48 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_DEF, val_peek(6).sval, (INodo)val_peek(4).obj, (INodo)val_peek(1).obj); }
break;
case 12:
//#line 49 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 13:
//#line 50 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 14:
//#line 53 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SEQ,(INodo)val_peek(1).obj,(INodo)val_peek(0).obj); }
break;
case 15:
//#line 54 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 16:
//#line 57 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 17:
//#line 58 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj, null); }
break;
case 18:
//#line 59 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 19:
//#line 62 "calc.y"
{ yyval.obj = new NodoNT(val_peek(0).sval); }
break;
case 20:
//#line 65 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 21:
//#line 66 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,null); }
break;
case 22:
//#line 67 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 23:
//#line 70 "calc.y"
{ yyval.obj = new NodoTDouble(val_peek(0).dval); }
break;
case 24:
//#line 71 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo)val_peek(0).obj); }
break;
case 25:
//#line 72 "calc.y"
{ yyval.obj = new NodoID(val_peek(0).sval); }
break;
case 26:
//#line 73 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ADD,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 27:
//#line 74 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SUB,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 28:
//#line 75 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.MULL,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 29:
//#line 76 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.DIV,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 30:
//#line 77 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LESS,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 31:
//#line 78 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.UMINUS,(INodo)val_peek(0).obj,null); }
break;
case 32:
//#line 79 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.POW,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 33:
//#line 80 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 34:
//#line 81 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_CALL,val_peek(3).sval,(INodo)val_peek(1).obj); }
break;
case 35:
//#line 84 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_BODY,(INodo)val_peek(3).obj,(INodo)val_peek(1).obj); }
break;
case 36:
//#line 85 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_BODY,(INodo)val_peek(0).obj,null); }
break;
case 37:
//#line 86 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_BODY,(INodo)new NodoNT(),(INodo)val_peek(1).obj); }
break;
//#line 671 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
