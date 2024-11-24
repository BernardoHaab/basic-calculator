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
public final static short IDENT=275;
public final static short NEG=278;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    3,    3,    3,    2,    2,    2,    2,    2,
    2,    2,    2,    2,    8,    8,    4,    4,    7,    7,
    7,    5,    6,    6,    6,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,
};
final static short yylen[] = {                            2,
    0,    2,    1,    2,    2,    2,    5,    7,    5,    9,
    6,    2,    3,    2,    3,    1,    2,    0,    3,    1,
    0,    1,    3,    1,    0,    1,    3,    1,    3,    3,
    3,    3,    3,    3,    3,    3,    2,    3,    3,    3,
    3,    3,    3,    2,    3,    3,    4,
};
final static short yydefred[] = {                         1,
    0,    3,   26,    0,    0,    0,    0,   18,    0,    2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   17,    5,    0,    0,    0,    0,    0,   46,   15,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,    0,    0,    0,    0,   14,    0,
    6,   47,    0,    0,    0,    0,    0,   13,    0,    0,
    0,    0,   22,   24,    0,    0,    9,    0,    0,    0,
    0,    0,   11,   23,    8,    0,    0,   10,
};
final static short yydgoto[] = {                          1,
   41,   42,   10,   11,   84,   85,   48,   12,
};
final static short yysindex[] = {                         0,
   -5,    0,    0,    9,   68,    9,    9,    0,  104,    0,
   -1, -255,  359,    9,    9,    9,    9,  210,   28,  -14,
    0,    9,    9,    9,    9,    9,    9,    9,    9,    9,
    9,    9,    9,    9,  -56,  -36,  -25,  -24, -258,    7,
  112,    0,    0,  359,  359,  359,  359,   -7,    0,    0,
  359,  359,  359,  359,  359,  359,  260,  260,   42,   42,
  210,  210,  210,    0,    9,    9,    9,  -19,    0,  120,
    0,    0,    9,   50,   59,  145, -253,    0,  359, -100,
 -100,    9,    0,    0,    1, -229,    0,  300, -100, -253,
 -100,    9,    0,    0,    0,   83, -100,    0,
};
final static short yyrindex[] = {                         0,
  203,    0,    0,    0,   20,    0,    0,    0,    0,    0,
 -124,    0,  -30,    0,    0,    0,    2,  -35,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   -3,   79,  117,   14,    0,    0,    0,
  134,  150,  231,  289,  323,  324,  393,  405,   72,  140,
  381,  388,  413,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   16,    0,   33,  -40,
  -40,    0,    0,    0,    0,  -27,    0,    0,  -40,    0,
  -40,    0,    0,    0,    0,    0,  -40,    0,
};
final static short yygindex[] = {                         0,
  483,    0,    0,   25,  -42,    0,    0,  -61,
};
final static int YYTABLESIZE=670;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         18,
   16,   43,   64,   65,   18,   44,   44,   44,   44,   44,
   37,   44,    7,   37,   66,   67,   68,    7,   86,   87,
   77,   83,    8,   44,   44,    7,   44,   93,   37,   95,
    6,   91,   20,   72,    7,   98,   73,   42,    7,    6,
   42,   89,   21,    6,   90,   21,    7,   94,    7,    0,
    0,    6,    0,    6,   20,   42,   25,   20,    0,   25,
   28,   28,   28,   28,   28,   69,   28,    0,   49,   32,
   31,    0,   30,   19,   33,    0,   19,    0,   28,   28,
    0,   28,    0,   32,   18,    0,    0,   28,   33,   29,
   80,   32,   31,    0,   30,    0,   33,    7,    0,   81,
   32,   31,    0,   30,    0,   33,    0,   17,    0,   28,
   50,   29,   30,   28,   30,   30,   30,    8,   28,   43,
   29,   34,   43,   97,   32,   31,    0,   30,   16,   33,
   30,   30,   16,   30,    0,   34,   16,   43,    0,    0,
    0,    0,   28,   34,   29,   32,   31,    0,   30,    0,
   33,    0,   34,   32,   31,    0,   30,   27,   33,    0,
   27,   32,   31,   28,   30,   29,   33,    0,    0,    0,
   71,   28,    0,   29,   41,   27,   34,   41,   78,   28,
   29,   29,   29,   29,   29,    0,   32,   31,    0,   30,
   38,   33,   41,   38,    0,    0,    0,   34,   29,   29,
    0,   29,    0,   82,   28,   34,   29,    0,   38,    0,
    0,    0,    0,   34,    0,   18,   18,   18,   18,   18,
   18,   44,   18,   18,   18,    0,   37,    0,    7,    7,
    7,    7,    7,   18,   18,    7,    7,    7,   34,    0,
    0,   35,    0,    3,   36,   37,    7,    7,   38,   39,
   40,    2,    3,   42,   35,    0,    3,   36,   37,    4,
    5,   38,   39,   40,    3,    0,    3,    0,    4,    5,
    0,   39,    4,    5,   39,    0,   28,    0,    0,    0,
    4,    5,    4,    5,    0,   28,   28,   28,   28,   39,
    0,   28,   28,   22,   23,   24,   25,    0,    0,   26,
   27,   32,   31,   34,   30,    0,   33,   22,   23,   24,
   25,    0,    0,   26,   27,   22,   23,   24,   25,    0,
    0,   26,   27,    0,   22,   23,   24,   25,   30,   40,
   26,   27,   40,    0,    0,   43,    0,   14,   15,    0,
    0,   32,   31,    0,   30,    0,   33,   40,   22,   23,
   24,   25,    0,   34,   26,   27,    0,    0,   92,   28,
   21,   29,    0,   36,   35,    0,   36,   35,    0,   22,
   23,   24,   25,   27,    0,   26,   27,   22,   23,   24,
   25,   36,   35,   26,   27,   22,   23,   24,   25,    0,
   41,   26,   27,   34,    0,    0,   29,    0,    0,    0,
   32,   31,    0,   30,    0,   33,   38,    0,    0,    0,
   22,   23,   24,   25,    0,    0,   26,   27,   28,    0,
   29,   31,   31,   31,   31,   31,    0,   31,   32,   32,
   32,   32,   32,   33,   32,    0,   33,    0,    0,   31,
   31,    0,   31,    0,    0,   34,   32,   32,   34,   32,
    0,   33,   34,   45,   45,   45,   45,   45,   18,   45,
    0,   18,   18,   34,    0,   18,   18,   18,    0,    0,
    0,   45,   45,    0,   45,   22,   23,   24,   25,    0,
    0,   26,   27,    9,    0,    0,   13,   39,   18,   19,
    0,    0,    0,    0,    0,    0,   44,   45,   46,   47,
    0,    0,    0,    0,   51,   52,   53,   54,   55,   56,
   57,   58,   59,   60,   61,   62,   63,    0,    0,    0,
    0,    0,   70,    0,    0,   22,   23,   24,   25,    0,
    0,   26,   27,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,    0,   74,   75,   76,
    0,    0,    0,    0,    0,   79,    0,    0,    0,    0,
    0,    0,    0,    0,   88,   22,   23,   24,   25,    0,
    0,   26,   27,    0,   96,    0,    0,    0,    0,   36,
   35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   22,   23,   24,   25,    0,    0,
   26,   27,    0,    0,    0,    0,    0,   31,    0,    0,
    0,    0,    0,    0,   32,    0,    0,    0,    0,   33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   34,    0,    0,    0,    0,    0,    0,    0,   45,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
  125,  257,   59,   40,   45,   41,   42,   43,   44,   45,
   41,   47,   40,   44,   40,   40,  275,   45,   80,   81,
   40,  275,  123,   59,   60,   40,   62,   89,   59,   91,
   45,  261,    8,   41,   40,   97,   44,   41,   40,   45,
   44,   41,   41,   45,   44,   44,   40,   90,   40,   -1,
   -1,   45,   -1,   45,   41,   59,   41,   44,   -1,   44,
   41,   42,   43,   44,   45,   59,   47,   -1,   41,   42,
   43,   -1,   45,   41,   47,   -1,   44,   -1,   59,   60,
   -1,   62,   -1,   42,  125,   -1,   -1,   60,   47,   62,
   41,   42,   43,   -1,   45,   -1,   47,  125,   -1,   41,
   42,   43,   -1,   45,   -1,   47,   -1,   40,   -1,   60,
  125,   62,   41,   94,   43,   44,   45,  123,   60,   41,
   62,   94,   44,   41,   42,   43,   -1,   45,   61,   47,
   59,   60,  257,   62,   -1,   94,  261,   59,   -1,   -1,
   -1,   -1,   60,   94,   62,   42,   43,   -1,   45,   -1,
   47,   -1,   94,   42,   43,   -1,   45,   41,   47,   -1,
   44,   42,   43,   60,   45,   62,   47,   -1,   -1,   -1,
   59,   60,   -1,   62,   41,   59,   94,   44,   59,   60,
   41,   62,   43,   44,   45,   -1,   42,   43,   -1,   45,
   41,   47,   59,   44,   -1,   -1,   -1,   94,   59,   60,
   -1,   62,   -1,   59,   60,   94,   62,   -1,   59,   -1,
   -1,   -1,   -1,   94,   -1,  256,  257,  258,  259,  260,
  261,  257,  263,  264,  265,   -1,  257,   -1,  256,  257,
  258,  259,  260,  274,  275,  263,  264,  265,   94,   -1,
   -1,  256,   -1,  258,  259,  260,  274,  275,  263,  264,
  265,  257,  258,  257,  256,   -1,  258,  259,  260,  274,
  275,  263,  264,  265,  258,   -1,  258,   -1,  274,  275,
   -1,   41,  274,  275,   44,   -1,  257,   -1,   -1,   -1,
  274,  275,  274,  275,   -1,  266,  267,  268,  269,   59,
   -1,  272,  273,  266,  267,  268,  269,   -1,   -1,  272,
  273,   42,   43,   94,   45,   -1,   47,  266,  267,  268,
  269,   -1,   -1,  272,  273,  266,  267,  268,  269,   -1,
   -1,  272,  273,   -1,  266,  267,  268,  269,  257,   41,
  272,  273,   44,   -1,   -1,  257,   -1,  270,  271,   -1,
   -1,   42,   43,   -1,   45,   -1,   47,   59,  266,  267,
  268,  269,   -1,   94,  272,  273,   -1,   -1,   59,   60,
  257,   62,   -1,   41,   41,   -1,   44,   44,   -1,  266,
  267,  268,  269,  257,   -1,  272,  273,  266,  267,  268,
  269,   59,   59,  272,  273,  266,  267,  268,  269,   -1,
  257,  272,  273,   94,   -1,   -1,  257,   -1,   -1,   -1,
   42,   43,   -1,   45,   -1,   47,  257,   -1,   -1,   -1,
  266,  267,  268,  269,   -1,   -1,  272,  273,   60,   -1,
   62,   41,   42,   43,   44,   45,   -1,   47,   41,   42,
   43,   44,   45,   41,   47,   -1,   44,   -1,   -1,   59,
   60,   -1,   62,   -1,   -1,   41,   59,   60,   44,   62,
   -1,   59,   94,   41,   42,   43,   44,   45,  256,   47,
   -1,  259,  260,   59,   -1,  263,  264,  265,   -1,   -1,
   -1,   59,   60,   -1,   62,  266,  267,  268,  269,   -1,
   -1,  272,  273,    1,   -1,   -1,    4,  257,    6,    7,
   -1,   -1,   -1,   -1,   -1,   -1,   14,   15,   16,   17,
   -1,   -1,   -1,   -1,   22,   23,   24,   25,   26,   27,
   28,   29,   30,   31,   32,   33,   34,   -1,   -1,   -1,
   -1,   -1,   40,   -1,   -1,  266,  267,  268,  269,   -1,
   -1,  272,  273,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  257,   -1,   65,   66,   67,
   -1,   -1,   -1,   -1,   -1,   73,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   82,  266,  267,  268,  269,   -1,
   -1,  272,  273,   -1,   92,   -1,   -1,   -1,   -1,  257,
  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  266,  267,  268,  269,   -1,   -1,
  272,  273,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
   -1,   -1,   -1,   -1,  257,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=278;
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
"FOR","DEF","RETURN","EQ","LTE","GTE","NOTEQ","ASSPLUS","ASSMULTI","OR","AND",
"NOT","IDENT","\">=\"","\"<=\"","NEG",
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

//#line 99 "calc.y"


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
//#line 447 "Parser.java"
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
//#line 58 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SEQ,(INodo)val_peek(1).obj,(INodo)val_peek(0).obj); }
break;
case 18:
//#line 59 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 19:
//#line 62 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 20:
//#line 63 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LEXP,(INodo)val_peek(0).obj, null); }
break;
case 21:
//#line 64 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 22:
//#line 67 "calc.y"
{ yyval.obj = new NodoNT(val_peek(0).sval); }
break;
case 23:
//#line 70 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,(INodo)val_peek(2).obj); }
break;
case 24:
//#line 71 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LPARAMS,(INodo)val_peek(0).obj,null); }
break;
case 25:
//#line 72 "calc.y"
{ yyval.obj = new NodoNT(); }
break;
case 26:
//#line 75 "calc.y"
{ yyval.obj = new NodoTDouble(val_peek(0).dval); }
break;
case 27:
//#line 76 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo)val_peek(0).obj); }
break;
case 28:
//#line 77 "calc.y"
{ yyval.obj = new NodoID(val_peek(0).sval); }
break;
case 29:
//#line 78 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ADD,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 30:
//#line 79 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.SUB,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 31:
//#line 80 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.MULL,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 32:
//#line 81 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.DIV,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 33:
//#line 82 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LESS,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 34:
//#line 83 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GT,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 35:
//#line 84 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.AND,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 36:
//#line 85 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.OR,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 37:
//#line 86 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.NOT,(INodo)val_peek(0).obj,null); }
break;
case 38:
//#line 87 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.LTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 39:
//#line 88 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.GTE,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 40:
//#line 89 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.NOTEQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 41:
//#line 90 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.EQ,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 42:
//#line 91 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.ADD, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 43:
//#line 92 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.ATRIB, val_peek(2).sval, (INodo) new NodoNT(TipoOperacao.MULL, (INodo) new NodoID(val_peek(2).sval), (INodo) val_peek(0).obj)); }
break;
case 44:
//#line 93 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.UMINUS,(INodo)val_peek(0).obj,null); }
break;
case 45:
//#line 94 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.POW,(INodo)val_peek(2).obj,(INodo)val_peek(0).obj); }
break;
case 46:
//#line 95 "calc.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 47:
//#line 96 "calc.y"
{ yyval.obj = new NodoNT(TipoOperacao.FUNC_CALL,val_peek(3).sval,(INodo)val_peek(1).obj); }
break;
//#line 790 "Parser.java"
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
