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
public final static short EQ=266;
public final static short LTE=267;
public final static short GTE=268;
public final static short NOTEQ=269;
public final static short ASSPLUS=270;
public final static short ASSMULTI=271;
public final static short IDENT=272;
public final static short NEG=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    8,    8,    4,    4,    7,    7,
    7,    5,    6,    6,    6,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    2,    2,    5,    7,    5,    9,
    6,    2,    3,    2,    3,    1,    2,    0,    3,    1,
    0,    1,    3,    1,    0,    1,    3,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    3,    3,    4,
};
final static short yydefred[] = {                         1,
    0,    3,   26,    0,    0,    0,   18,    0,    2,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   17,    5,    0,
    0,    0,    0,    0,   43,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   12,    0,    0,
    0,    0,   14,    0,    6,   44,    0,    0,    0,    0,
    0,   13,    0,    0,    0,    0,   22,   24,    0,    0,
    9,    0,    0,    0,    0,    0,   11,   23,    8,    0,
    0,   10,
};
final static short yydgoto[] = {                          1,
   37,   38,    9,   10,   78,   79,   44,   11,
};
final static short yysindex[] = {                         0,
   -1,    0,    0,  -12,  -11,  -11,    0,   69,    0,  -10,
 -256,  -11,  -11,  -11,  -11,  -91,   16,  -20,    0,  -11,
  -11,  -11,  -11,  -11,  -11,  -11,  -11,  -11,  -11,  -11,
  -57,  -24,  -18,  -17, -245,  -21,   76,    0,    0,  147,
  147,  147,  147,  -35,    0,    0,  147,  147,  147,  147,
  222,  222,   23,   23,  -91,  -91,  -91,    0,  -11,  -11,
  -11,   -3,    0,   85,    0,    0,  -11,   32,   39,  106,
 -231,    0,  147,  -78,  -78,  -11,    0,    0,  -27, -214,
    0,  112,  -78, -231,  -78,  -11,    0,    0,    0,   46,
  -78,    0,
};
final static short yyrindex[] = {                         0,
 -252,    0,    0,    9,    0,    0,    0,    0,    0,  -99,
    0,    0,    0,    0,  -23,  139,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   93,
  102,  229,   -8,    0,    0,    0,  252,  280,  281,  317,
  353,  367,  152,  358,  287,  323,  345,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    2,    0,   56,  -40,  -40,    0,    0,    0,    0,  -30,
    0,    0,  -40,    0,  -40,    0,    0,    0,    0,    0,
  -40,    0,
};
final static short yygindex[] = {                         0,
  418,    0,    0,   48,  -22,    0,    0,  -43,
};
final static int YYTABLESIZE=624;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   39,   58,   30,   18,   18,   66,   18,   18,   67,    7,
   18,   18,   18,   83,    7,   59,   84,   21,    6,    6,
   21,   60,   61,    5,    5,   16,   62,   15,    6,    6,
   80,   81,   20,    5,    5,   20,   71,   63,    6,   87,
   77,   89,   25,    5,    7,   25,   85,   92,   14,   28,
   28,   28,   28,   28,   18,   28,   45,   28,   27,    0,
   26,   88,   29,    0,   28,    0,    0,   28,   28,   29,
   28,    0,   74,   28,   27,   24,   26,   25,   29,   75,
   28,   27,    0,   26,   18,   29,   91,   28,   27,    0,
   26,   24,   29,   25,    7,    0,   19,    0,   24,   19,
   25,    0,   28,    0,   46,   24,    0,   25,    0,   30,
   28,   27,    0,   26,    0,   29,   30,   28,   27,    0,
   26,    7,   29,    0,    0,   30,   28,   27,   24,   26,
   25,   29,   30,   39,   65,   24,   39,   25,    0,   30,
    0,    0,   40,   72,   24,   40,   25,   28,   27,    0,
   26,   39,   29,   28,   27,    0,   26,   16,   29,    0,
   40,   16,   30,    0,   76,   24,    0,   25,    0,   30,
   86,   24,    0,   25,   20,   21,   22,   23,   30,   41,
   41,   41,   41,   41,    0,   41,    0,    0,   28,   27,
    0,   26,   30,   29,   30,   30,   30,   41,   41,   30,
   41,    0,    0,    0,    0,   30,   24,    0,   25,    0,
   30,   30,    0,   30,    0,   18,   18,   18,   18,   18,
   18,    0,   18,   18,   18,    7,    7,    7,    7,    7,
    0,   18,    7,    7,    7,   31,    3,    3,   32,   33,
   30,    7,   34,   35,   36,   31,    3,    3,   32,   33,
    4,    4,   34,   35,   36,    2,    3,   12,   13,    0,
    4,    4,    0,   28,   27,   28,   26,    0,   29,   27,
    4,    0,   27,    0,   28,   28,   28,   28,    0,    0,
    0,   20,   21,   22,   23,    0,    0,   27,   20,   21,
   22,   23,   38,    0,    0,   38,    0,   20,   21,   22,
   23,    0,    0,    0,   20,   21,   22,   23,    0,    0,
   38,   20,   21,   22,   23,   30,    0,    0,    0,    0,
   35,   36,    0,   35,   36,   19,    0,   31,   31,   31,
   31,   31,    0,   31,   20,   21,   22,   23,   35,   36,
    0,   20,   21,   22,   23,   31,   31,    0,   31,   39,
   20,   21,   22,   23,    0,    0,    0,   37,   40,    0,
   37,    0,    0,   32,   32,   32,   32,   32,    0,   32,
    0,   20,   21,   22,   23,   37,    0,   20,   21,   22,
   23,   32,   32,    0,   32,   42,   42,   42,   42,   42,
    0,   42,    0,   33,    0,   41,   33,    0,   29,    0,
   29,   29,   29,   42,   42,    0,   42,   34,   30,    0,
   34,   33,   20,   21,   22,   23,   29,   29,    8,   29,
    0,    0,   16,   17,    0,   34,    0,    0,    0,   40,
   41,   42,   43,    0,    0,    0,    0,   47,   48,   49,
   50,   51,   52,   53,   54,   55,   56,   57,    0,    0,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   68,   69,   70,    0,
    0,    0,    0,    0,   73,   27,    0,   20,   21,   22,
   23,    0,    0,   82,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   90,    0,    0,    0,    0,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   35,   36,    0,    0,
    0,    0,    0,   31,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   37,    0,    0,    0,    0,    0,   32,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   42,    0,    0,    0,    0,    0,    0,    0,   33,
    0,    0,    0,    0,   29,    0,    0,    0,    0,    0,
    0,    0,    0,   34,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  257,   59,   94,  256,   45,   41,  259,  260,   44,   40,
  263,  264,  265,   41,   45,   40,   44,   41,   40,   40,
   44,   40,   40,   45,   45,  125,  272,   40,   40,   40,
   74,   75,   41,   45,   45,   44,   40,   59,   40,   83,
  272,   85,   41,   45,  123,   44,  261,   91,   61,   41,
   42,   43,   44,   45,    7,   47,   41,   42,   43,   -1,
   45,   84,   47,   -1,   42,   -1,   -1,   59,   60,   47,
   62,   -1,   41,   42,   43,   60,   45,   62,   47,   41,
   42,   43,   -1,   45,  125,   47,   41,   42,   43,   -1,
   45,   60,   47,   62,  125,   -1,   41,   -1,   60,   44,
   62,   -1,   94,   -1,  125,   60,   -1,   62,   -1,   94,
   42,   43,   -1,   45,   -1,   47,   94,   42,   43,   -1,
   45,  123,   47,   -1,   -1,   94,   42,   43,   60,   45,
   62,   47,   94,   41,   59,   60,   44,   62,   -1,   94,
   -1,   -1,   41,   59,   60,   44,   62,   42,   43,   -1,
   45,   59,   47,   42,   43,   -1,   45,  257,   47,   -1,
   59,  261,   94,   -1,   59,   60,   -1,   62,   -1,   94,
   59,   60,   -1,   62,  266,  267,  268,  269,   94,   41,
   42,   43,   44,   45,   -1,   47,   -1,   -1,   42,   43,
   -1,   45,   41,   47,   43,   44,   45,   59,   60,   94,
   62,   -1,   -1,   -1,   -1,   94,   60,   -1,   62,   -1,
   59,   60,   -1,   62,   -1,  256,  257,  258,  259,  260,
  261,   -1,  263,  264,  265,  256,  257,  258,  259,  260,
   -1,  272,  263,  264,  265,  256,  258,  258,  259,  260,
   94,  272,  263,  264,  265,  256,  258,  258,  259,  260,
  272,  272,  263,  264,  265,  257,  258,  270,  271,   -1,
  272,  272,   -1,   42,   43,  257,   45,   -1,   47,   41,
  272,   -1,   44,   -1,  266,  267,  268,  269,   -1,   -1,
   -1,  266,  267,  268,  269,   -1,   -1,   59,  266,  267,
  268,  269,   41,   -1,   -1,   44,   -1,  266,  267,  268,
  269,   -1,   -1,   -1,  266,  267,  268,  269,   -1,   -1,
   59,  266,  267,  268,  269,   94,   -1,   -1,   -1,   -1,
   41,   41,   -1,   44,   44,  257,   -1,   41,   42,   43,
   44,   45,   -1,   47,  266,  267,  268,  269,   59,   59,
   -1,  266,  267,  268,  269,   59,   60,   -1,   62,  257,
  266,  267,  268,  269,   -1,   -1,   -1,   41,  257,   -1,
   44,   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,
   -1,  266,  267,  268,  269,   59,   -1,  266,  267,  268,
  269,   59,   60,   -1,   62,   41,   42,   43,   44,   45,
   -1,   47,   -1,   41,   -1,  257,   44,   -1,   41,   -1,
   43,   44,   45,   59,   60,   -1,   62,   41,  257,   -1,
   44,   59,  266,  267,  268,  269,   59,   60,    1,   62,
   -1,   -1,    5,    6,   -1,   59,   -1,   -1,   -1,   12,
   13,   14,   15,   -1,   -1,   -1,   -1,   20,   21,   22,
   23,   24,   25,   26,   27,   28,   29,   30,   -1,   -1,
   -1,   -1,   -1,   36,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   59,   60,   61,   -1,
   -1,   -1,   -1,   -1,   67,  257,   -1,  266,  267,  268,
  269,   -1,   -1,   76,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   86,   -1,   -1,   -1,   -1,  257,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  257,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  257,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
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
"FOR","DEF","RETURN","EQ","LTE","GTE","NOTEQ","ASSPLUS","ASSMULTI","IDENT",
"\"<=\"","NEG",
};
final static String yyrule[] = {
"$accept : input",
"input :",
"input : input line",
"line : NL",
"line : exp NL",
"line : block_lcmd NL",
"cmd : exp ';'",
"cmd : IF '(' exp ')' block_lcmd",
"cmd : IF '(' exp ')' block_lcmd ELSE block_lcmd",
"cmd : WHILE '(' exp ')' block_lcmd",
"cmd : FOR '(' exp ';' exp ';' exp ')' block_lcmd",
"cmd : DEF IDENT '(' lparams ')' block_lcmd",
"cmd : error ';'",
"cmd : RETURN exp ';'",
"cmd : RETURN ';'",
"block_lcmd : '{' lcmd '}'",
"block_lcmd : lcmd",
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
"exp : exp '>' exp",
"exp : exp LTE exp",
"exp : exp GTE exp",
"exp : exp NOTEQ exp",
"exp : exp EQ exp",
"exp : IDENT ASSPLUS exp",
"exp : IDENT ASSMULTI exp",
"exp : '-' exp",
"exp : exp '^' exp",
"exp : '(' exp ')'",
"exp : IDENT '(' lexp ')'",
};

//#line 97 "calc.y"


  public static Stack<HashMap<String, ResultValue>> memoryStack = new Stack<>();
  public static HashMap<String, NodoNT> functions = new HashMap<>();
  public static boolean isReturning = false;
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
    memoryStack.push(new HashMap<>());
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
//#line 433 "Parser.java"
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
//#line 25 "calc.y"
{yyval.obj=null;}
break;
case 2:
//#line 26 "calc.y"
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
//#line 37 "calc.y"
{ if (interactive) System.out.print("\n> "); yyval.obj = null; }
break;
case 4:
//#line 38 "calc.y"
{ yyval.obj = val_peek(1).obj;
		   System.out.println("\n= " + val_peek(1).obj);
                   if (interactive) System.out.print("\n>: "); }
break;
case 6:
//#line 44 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 7:
//#line 45 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.IF,(INodo)val_peek(2).obj, (INodo)val_peek(0).obj, null); }
break;
case 8:
//#line 46 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.IFELSE,(INodo)val_peek(4).obj, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 9:
//#line 47 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.WHILE,(INodo)val_peek(2).obj, (INodo)val_peek(0).obj, null); }
break;
case 10:
//#line 48 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FOR, (INodo)val_peek(6).obj, (INodo)val_peek(4).obj, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 11:
//#line 49 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_DEF, val_peek(4).sval, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 12:
//#line 50 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 13:
//#line 51 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.RETURN, (INodo)val_peek(1).obj, null); }
break;
case 14:
//#line 52 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.RETURN,(INodo) new NodoNT(), null); }
break;
case 15:
//#line 55 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 16:
//#line 56 "calc.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 17:
//#line 59 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SEQ,(INodo)val_peek(1).obj,(INodo)val_peek(0).obj); }
break;
case 18:
//#line 60 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 19:
//#line 63 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 20:
//#line 64 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj, null); }
break;
case 21:
//#line 65 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 22:
//#line 68 "calc.y"
{ yyval.obj = new NodoNT(val_peek(0).sval); }
break;
case 23:
//#line 71 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 24:
//#line 72 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,null); }
break;
case 25:
//#line 73 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 26:
//#line 76 "calc.y"
{ yyval.obj = new NodoTDouble(val_peek(0).dval); }
break;
case 27:
//#line 77 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo)val_peek(0).obj); }
break;
case 28:
//#line 78 "calc.y"
{ yyval.obj = new NodoID(val_peek(0).sval); }
break;
case 29:
//#line 79 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ADD,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 30:
//#line 80 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SUB,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 31:
//#line 81 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.MULL,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 32:
//#line 82 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.DIV,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 33:
//#line 83 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LESS,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 34:
//#line 84 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GT,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 35:
//#line 85 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 36:
//#line 86 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 37:
//#line 87 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.NOTEQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 38:
//#line 88 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.EQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 39:
//#line 89 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.ADD, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 40:
//#line 90 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.MULL, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 41:
//#line 91 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.UMINUS,(INodo)val_peek(0).obj,null); }
break;
case 42:
//#line 92 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.POW,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 43:
//#line 93 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 44:
//#line 94 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_CALL,val_peek(3).sval,(INodo)val_peek(1).obj); }
break;
//#line 764 "Parser.java"
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
