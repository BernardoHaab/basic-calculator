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
public final static short OR=272;
public final static short AND=273;
public final static short NOT=274;
public final static short SHOW=275;
public final static short help=276;
public final static short IDENT=277;
public final static short NEG=280;
public final static short SHOW_ALL=281;
public final static short HELP=282;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    8,    8,    8,    9,    9,    9,
    4,    4,    7,    7,    7,    5,    6,    6,    6,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    2,    2,    5,    7,    5,    9,
    6,    2,    3,    2,    3,    1,    2,    2,    1,    1,
    2,    0,    3,    1,    0,    1,    3,    1,    0,    1,
    3,    1,    3,    3,    3,    3,    3,    3,    3,    3,
    2,    3,    3,    3,    3,    3,    3,    2,    3,    3,
    4,
};
final static short yydefred[] = {                         1,
    0,    3,   30,    0,    0,    0,    0,   22,    0,    0,
    2,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   19,   20,   17,    4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   21,    5,    0,    0,
    0,    0,    0,   50,   15,   18,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   12,
    0,    0,    0,    0,   14,    0,    6,   51,    0,    0,
    0,    0,    0,   13,    0,    0,    0,    0,   26,   28,
    0,    0,    9,    0,    0,    0,    0,    0,   11,   27,
    8,    0,    0,   10,
};
final static short yydgoto[] = {                          1,
   46,   47,   11,   12,   90,   91,   53,   13,   25,
};
final static short yysindex[] = {                         0,
  -35,    0,    0,  -37,  -18,  -37,  -37,    0, -256,  113,
    0,   31, -253,  378,  -37,  -37,  -37,  -37,  208,   63,
   11, -270,    0,    0,    0,    0,  -37,  -37,  -37,  -37,
  -37,  -37,  -37,  -37,  -37,  -37,  -37,  -37,  -37,  -48,
  -28,  -24,  -23, -249,  -39,  121,    0,    0,  378,  378,
  378,  378,  -14,    0,    0,    0,  378,  378,  378,  378,
  378,  378,  163,  163,  262,  262,  208,  208,  208,    0,
  -37,  -37,  -37,  -19,    0,  142,    0,    0,  -37,   77,
   85,  219, -248,    0,  378,  -22,  -22,  -37,    0,    0,
   -6, -224,    0,  357,  -22, -248,  -22,  -37,    0,    0,
    0,   99,  -22,    0,
};
final static short yyrindex[] = {                         0,
  234,    0,    0,    0,   55,    0,    0,    0,    0,    0,
    0, -123,    0,  -26,    0,    0,    0,   -2,    3,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   16,   20,
   36,    8,    0,    0,    0,    0,  150,  155,  200,  266,
  296,  318,  319,  332,  405,  411,   25,  256,  400,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   14,    0,   37,  -31,  -31,    0,    0,    0,
    0,   -9,    0,    0,  -31,    0,  -31,    0,    0,    0,
    0,    0,  -31,    0,
};
final static short yygindex[] = {                         0,
  604,    0,    0,   33,  -43,    0,    0,  -63,    0,
};
final static int YYTABLESIZE=702;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                          9,
    7,   16,    7,   48,    7,    6,   56,    6,   22,    6,
   70,   71,    9,   22,   41,   72,   73,   41,   22,   75,
   83,   18,   92,   93,   23,   24,   78,   74,   89,   79,
    7,   99,   41,  101,   95,    7,   97,   96,   25,  104,
   21,   25,   17,   48,   48,   48,   48,   48,   24,   48,
    7,   24,  100,    0,   29,    6,   46,   29,    0,   46,
   47,   48,   48,   47,   48,   35,   35,   35,   35,   35,
    7,   35,    0,    0,   46,    6,   31,   23,   47,   31,
   23,    0,    0,   35,   35,    0,   35,    8,    0,    0,
    0,    0,    0,   22,   31,   32,   32,   32,   32,   32,
    8,   32,    0,   54,   37,   36,    0,   35,    0,   38,
    0,    0,    0,   32,   32,    7,   32,   86,   37,   36,
    0,   35,   33,   38,   34,   87,   37,   36,    0,   35,
    0,   38,    0,   16,    0,   55,   33,   16,   34,  103,
   37,   36,    0,   35,   33,   38,   34,    0,   32,    0,
    0,    0,    0,    0,   37,   36,   39,   35,   33,   38,
   34,    0,   37,   36,    0,   35,    0,   38,    0,    0,
   39,    0,   33,    0,   34,    0,    0,    0,   39,   77,
   33,    0,   34,   37,   36,    0,   35,    0,   38,    0,
   45,    0,   39,   45,    0,   42,    0,    0,   42,    0,
   84,   33,    0,   34,   37,   36,   39,   35,   45,   38,
    0,    0,    0,   42,   39,    0,    0,    0,    3,    0,
    3,    2,    3,    0,   22,   22,   22,   22,   22,   22,
   41,   22,   22,   22,    4,   39,    4,    5,    4,    5,
   43,    5,   22,   43,    0,   22,    7,    7,    7,    7,
    7,   15,   16,    7,    7,    7,   39,    0,   43,   48,
   37,   36,    0,   35,    7,   38,   40,    7,    3,   41,
   42,    0,   46,   43,   44,   45,   47,   88,   33,    0,
   34,   35,    0,    0,    4,    0,   40,    5,    3,   41,
   42,    0,   31,   43,   44,   45,   36,   36,   36,   36,
   36,   39,   36,   37,    4,    0,   44,    5,   38,   44,
    0,   32,   39,    0,   36,   36,    0,   36,    0,    0,
   32,   32,   32,   32,   44,    0,   32,   32,   27,   28,
   29,   30,    0,    0,   31,   32,   40,    0,    0,   40,
    0,    0,   27,   28,   29,   30,    0,    0,   31,   32,
   27,   28,   29,   30,   40,   39,   31,   32,   39,   37,
    0,   39,   37,    0,   27,   28,   29,   30,    0,   26,
   31,   32,   38,    0,    0,   38,   39,   37,   27,   28,
   29,   30,    0,    0,   31,   32,   27,   28,   29,   30,
   38,    0,   31,   32,    0,    0,    0,    0,   37,   36,
    0,   35,    0,   38,    0,    0,   45,   27,   28,   29,
   30,   42,    0,   31,   32,   98,   33,    0,   34,   37,
   36,    0,   35,    0,   38,    0,    0,    0,   27,   28,
   29,   30,    0,    0,   31,   32,    0,   33,    0,   34,
   49,   49,   49,   49,   49,   34,   49,   34,   34,   34,
   39,   33,    0,   33,   33,   33,   43,    0,   49,   49,
    0,   49,    0,   34,   34,    0,   34,    0,    0,   33,
   33,   39,   33,   27,   28,   29,   30,    0,    0,   31,
   32,    0,    0,    0,   27,   28,   29,   30,    0,   22,
   31,   32,   22,   22,    0,    0,   22,   22,   22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   36,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   44,    0,    0,    0,    0,   27,   28,   29,
   30,    0,    0,   31,   32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   39,   37,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   38,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   10,    0,    0,   14,    0,   19,
   20,    0,    0,    0,    0,    0,    0,    0,   49,   50,
   51,   52,   27,   28,   29,   30,    0,    0,   31,   32,
   57,   58,   59,   60,   61,   62,   63,   64,   65,   66,
   67,   68,   69,   27,   28,   29,   30,    0,   76,   31,
   32,    0,    0,    0,    0,    0,   49,    0,    0,    0,
    0,   34,    0,    0,    0,    0,    0,   33,    0,    0,
    0,    0,    0,    0,   80,   81,   82,    0,    0,    0,
    0,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,   94,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  102,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         35,
   40,  125,   40,  257,   40,   45,  277,   45,   40,   45,
   59,   40,   35,   45,   41,   40,   40,   44,  275,   59,
   40,   40,   86,   87,  281,  282,   41,  277,  277,   44,
   40,   95,   59,   97,   41,   45,  261,   44,   41,  103,
    8,   44,   61,   41,   42,   43,   44,   45,   41,   47,
   40,   44,   96,   -1,   41,   45,   41,   44,   -1,   44,
   41,   59,   60,   44,   62,   41,   42,   43,   44,   45,
   40,   47,   -1,   -1,   59,   45,   41,   41,   59,   44,
   44,   -1,   -1,   59,   60,   -1,   62,  123,   -1,   -1,
   -1,   -1,   -1,  125,   59,   41,   42,   43,   44,   45,
  123,   47,   -1,   41,   42,   43,   -1,   45,   -1,   47,
   -1,   -1,   -1,   59,   60,  125,   62,   41,   42,   43,
   -1,   45,   60,   47,   62,   41,   42,   43,   -1,   45,
   -1,   47,   -1,  257,   -1,  125,   60,  261,   62,   41,
   42,   43,   -1,   45,   60,   47,   62,   -1,   94,   -1,
   -1,   -1,   -1,   -1,   42,   43,   94,   45,   60,   47,
   62,   -1,   42,   43,   -1,   45,   -1,   47,   -1,   -1,
   94,   -1,   60,   -1,   62,   -1,   -1,   -1,   94,   59,
   60,   -1,   62,   42,   43,   -1,   45,   -1,   47,   -1,
   41,   -1,   94,   44,   -1,   41,   -1,   -1,   44,   -1,
   59,   60,   -1,   62,   42,   43,   94,   45,   59,   47,
   -1,   -1,   -1,   59,   94,   -1,   -1,   -1,  258,   -1,
  258,  257,  258,   -1,  256,  257,  258,  259,  260,  261,
  257,  263,  264,  265,  274,   94,  274,  277,  274,  277,
   41,  277,  274,   44,   -1,  277,  256,  257,  258,  259,
  260,  270,  271,  263,  264,  265,   94,   -1,   59,  257,
   42,   43,   -1,   45,  274,   47,  256,  277,  258,  259,
  260,   -1,  257,  263,  264,  265,  257,   59,   60,   -1,
   62,  257,   -1,   -1,  274,   -1,  256,  277,  258,  259,
  260,   -1,  257,  263,  264,  265,   41,   42,   43,   44,
   45,   94,   47,   42,  274,   -1,   41,  277,   47,   44,
   -1,  257,   94,   -1,   59,   60,   -1,   62,   -1,   -1,
  266,  267,  268,  269,   59,   -1,  272,  273,  266,  267,
  268,  269,   -1,   -1,  272,  273,   41,   -1,   -1,   44,
   -1,   -1,  266,  267,  268,  269,   -1,   -1,  272,  273,
  266,  267,  268,  269,   59,   94,  272,  273,   41,   41,
   -1,   44,   44,   -1,  266,  267,  268,  269,   -1,  257,
  272,  273,   41,   -1,   -1,   44,   59,   59,  266,  267,
  268,  269,   -1,   -1,  272,  273,  266,  267,  268,  269,
   59,   -1,  272,  273,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   -1,   47,   -1,   -1,  257,  266,  267,  268,
  269,  257,   -1,  272,  273,   59,   60,   -1,   62,   42,
   43,   -1,   45,   -1,   47,   -1,   -1,   -1,  266,  267,
  268,  269,   -1,   -1,  272,  273,   -1,   60,   -1,   62,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   94,   41,   -1,   43,   44,   45,  257,   -1,   59,   60,
   -1,   62,   -1,   59,   60,   -1,   62,   -1,   -1,   59,
   60,   94,   62,  266,  267,  268,  269,   -1,   -1,  272,
  273,   -1,   -1,   -1,  266,  267,  268,  269,   -1,  256,
  272,  273,  259,  260,   -1,   -1,  263,  264,  265,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   -1,   -1,   -1,  266,  267,  268,
  269,   -1,   -1,  272,  273,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  257,  257,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,    1,   -1,   -1,    4,   -1,    6,
    7,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   15,   16,
   17,   18,  266,  267,  268,  269,   -1,   -1,  272,  273,
   27,   28,   29,   30,   31,   32,   33,   34,   35,   36,
   37,   38,   39,  266,  267,  268,  269,   -1,   45,  272,
  273,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   -1,   -1,   -1,   -1,   71,   72,   73,   -1,   -1,   -1,
   -1,   -1,   79,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   88,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   98,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=282;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,"'#'",null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,"'^'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"NL","NUM","IF","WHILE","ELSE",
"PRINT","FOR","DEF","RETURN","EQ","LTE","GTE","NOTEQ","ASSPLUS","ASSMULTI","OR",
"AND","NOT","SHOW","help","IDENT","\">=\"","\"<=\"","NEG","SHOW_ALL","HELP",
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
"block_lcmd : '#' control",
"control : SHOW IDENT",
"control : SHOW_ALL",
"control : HELP",
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
"exp : exp AND exp",
"exp : exp OR exp",
"exp : NOT exp",
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

//#line 105 "calc.y"


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

  public void showIdent(String ident) {
    System.out.println("Memoria: "+memoryStack.peek().get(ident));
    System.out.println("Função: "+functions.get(ident));
  }

  public void showAll() {
    System.out.println("Memoria: "+memoryStack);
    System.out.println("Funções: "+functions);
  }

  public void help() {
    System.out.println("Funcionalidades básicas da calculadora:");
    System.out.println("1. Operações aritméticas: +, -, *, /, ^");
    System.out.println("2. Comparações: <, >, <=, >=, ==, !=");
    System.out.println("3. Operadores lógicos: &&, ||, !");
    System.out.println("4. Atribuições: =, +=, *=");
    System.out.println("5. Estruturas de controle: if, else, while, for");
    System.out.println("6. Definição de funções: define");
    System.out.println("7. Comandos de retorno: return");
    System.out.println("8. Comandos de exibição: show, show_all");
    System.out.println("9. Comando de ajuda: help");
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
//#line 490 "Parser.java"
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
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_DEF, val_peek(4).sval, (INodo)val_peek(2).obj, (INodo)val_peek(0).obj); }
break;
case 12:
//#line 49 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 13:
//#line 50 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.RETURN, (INodo)val_peek(1).obj, null); }
break;
case 14:
//#line 51 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.RETURN,(INodo) new NodoNT(), null); }
break;
case 15:
//#line 54 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 16:
//#line 55 "calc.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 17:
//#line 56 "calc.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 18:
//#line 59 "calc.y"
{ showIdent(val_peek(0).sval); }
break;
case 19:
//#line 60 "calc.y"
{ showAll(); }
break;
case 20:
//#line 61 "calc.y"
{ help(); }
break;
case 21:
//#line 64 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SEQ,(INodo)val_peek(1).obj,(INodo)val_peek(0).obj); }
break;
case 22:
//#line 65 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 23:
//#line 68 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 24:
//#line 69 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj, null); }
break;
case 25:
//#line 70 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 26:
//#line 73 "calc.y"
{ yyval.obj = new NodoNT(val_peek(0).sval); }
break;
case 27:
//#line 76 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 28:
//#line 77 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,null); }
break;
case 29:
//#line 78 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 30:
//#line 81 "calc.y"
{ yyval.obj = new NodoTDouble(val_peek(0).dval); }
break;
case 31:
//#line 82 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo)val_peek(0).obj); }
break;
case 32:
//#line 83 "calc.y"
{ yyval.obj = new NodoID(val_peek(0).sval); }
break;
case 33:
//#line 84 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ADD,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 34:
//#line 85 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SUB,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 35:
//#line 86 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.MULL,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 36:
//#line 87 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.DIV,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 37:
//#line 88 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LESS,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 38:
//#line 89 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GT,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 39:
//#line 90 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.AND,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 40:
//#line 91 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.OR,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 41:
//#line 92 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.NOT,(INodo)val_peek(0).obj,null); }
break;
case 42:
//#line 93 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 43:
//#line 94 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 44:
//#line 95 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.NOTEQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 45:
//#line 96 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.EQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 46:
//#line 97 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.ADD, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 47:
//#line 98 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.MULL, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 48:
//#line 99 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.UMINUS,(INodo)val_peek(0).obj,null); }
break;
case 49:
//#line 100 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.POW,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 50:
//#line 101 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 51:
//#line 102 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_CALL,val_peek(3).sval,(INodo)val_peek(1).obj); }
break;
//#line 849 "Parser.java"
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
