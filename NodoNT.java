public class NodoNT implements INodo {
  private TipoOperacao op;
  private INodo subE, subD;
  private INodo expr;
  private INodo forCmd;
  private String ident;

  // ADD, SUB, MULL, DIV, UMMINUS(...)
  public NodoNT(TipoOperacao op, INodo se, INodo sd) {
    this.op = op;
    subE = se;
    subD = sd;
  }

  // lParams
  public NodoNT(INodo se, String id) {
    this.op = TipoOperacao.LPARAMS;
    subE = se;
    ident = id;
  }

  // Param
  public NodoNT(String id) {
    this.op = TipoOperacao.PARAM;
    ident = id;
  }

  // Atribuiçao, Function call
  public NodoNT(TipoOperacao op, String id, INodo se) {
    this.op = op;
    subE = se;
    ident = id;
  }

  // If e IfElse
  public NodoNT(TipoOperacao op, INodo exp, INodo caseT, INodo caseF) {
    this.op = op;
    subE = caseT;
    subD = caseF;
    expr = exp;
  }

  // For
  public NodoNT(TipoOperacao op, INodo caseT, INodo exp, INodo caseF, INodo forCmd) {
    this.op = op;
    subE = caseT; // 1 parametro
    expr = exp; // 2 parametro
    subD = caseF; // 3 parametro
    this.forCmd = forCmd;
  }

  // Function definition
  public NodoNT(TipoOperacao op, String id, INodo params, INodo body) {
    this.op = op;
    ident = id;
    subE = params;
    subD = body;
  }

  public NodoNT() {
    op = TipoOperacao.NULL;
  }

  private void addToMemory(ResultValue value) {
    String context = Parser.contextStack.peek();

    SymbolTable table = Parser.contextTable.get(context);
    if (table != null) {
      table.put(ident, value);
    } else {
      table = new SymbolTable();
      table.put(ident, value);
      Parser.contextTable.put(context, table);
    }
  }

  public ResultValue avalia() {

    ResultValue result = null;
    ResultValue left, right, expressao;

    if (op == TipoOperacao.NULL)
      return null;

    if (op == TipoOperacao.UMINUS)
      result = new ResultValue(-1.0 * subE.avalia().getDouble());

    else if (op == TipoOperacao.ATRIB) {
      result = subE.avalia();
      Parser.memory.put(ident, result);
      addToMemory(result);
    }

    else if (op == TipoOperacao.IF) {
      expressao = expr.avalia();
      if (expressao.getBool())
        result = subE.avalia();
    }

    else if (op == TipoOperacao.IFELSE) {
      expressao = expr.avalia();
      if (expressao.getBool())
        result = subE.avalia();
      else
        result = subD.avalia();
    }

    else if (op == TipoOperacao.WHILE) {
      while ((expr.avalia()).getBool()) {
        subE.avalia();
      }
    } else if (op == TipoOperacao.SEQ) {
      subE.avalia();
      subD.avalia();

    } else if (op == TipoOperacao.FOR) {
      subE.avalia();
      while ((expr.avalia()).getBool()) {
        forCmd.avalia();
        subD.avalia();
      }
    }

    else if (op == TipoOperacao.LPARAMS) {
      subE.avalia();
      addToMemory(new ResultValue(0));
    }

    else if (op == TipoOperacao.PARAM) {
      result = new ResultValue(0);
      addToMemory(result);
    }

    else if (op == TipoOperacao.FUNC_DEF) {
      result = new ResultValue(subD);
      addToMemory(result);
      Parser.contextStack.push(ident);
      subE.avalia();
      Parser.contextStack.pop();
    }

    else if (op == TipoOperacao.FUNC_CALL) {
      SymbolTable table = Parser.contextTable.get(ident);
      Parser.contextStack.push(ident);
      // ToDo: corrigir parametros
      ResultValue body = table.get(ident);
      result = body.getFunction().avalia();
    }

    else {
      left = subE.avalia();
      right = subD.avalia();
      switch (op) {
        case ADD:
          result = new ResultValue((left.getDouble() + right.getDouble()));
          break;
        case SUB:
          result = new ResultValue(left.getDouble() - right.getDouble());
          break;
        case MULL:
          result = new ResultValue(left.getDouble() * right.getDouble());
          break;
        case DIV:
          result = new ResultValue(left.getDouble() / right.getDouble());
          break;
        case POW:
          result = new ResultValue(Math.pow(left.getDouble(), right.getDouble()));
          break;
        case LESS:
          result = new ResultValue(left.getDouble() < right.getDouble());
          break;
        default:
          result = new ResultValue(0);
      }
    }

    return result;
  }

  public String toString() {
    String opBin, result;
    if (op == TipoOperacao.ATRIB)
      result = ident + "=" + subE;
    else if (op == TipoOperacao.IF)
      result = "if (" + expr + ")" + subE + " else " + subD;
    else if (op == TipoOperacao.WHILE)
      result = "while (" + subE + ")" + subD;
    else if (op == TipoOperacao.UMINUS)
      result = "-" + subE;
    else {
      switch (op) {

        case ADD:
          opBin = " + ";
          break;
        case SUB:
          opBin = " - ";
          break;
        case MULL:
          opBin = " * ";
          break;
        case DIV:
          opBin = " / ";
          break;
        case POW:
          opBin = " ^ ";
          break;

        case LESS:
          opBin = " < ";
          break;

        default:
          opBin = " ? ";
      }
      result = "(" + subE + opBin + subD + ")";
    }
    return result;
  }

}
