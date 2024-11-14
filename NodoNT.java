import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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
    // String context = Parser.contextStack.peek();
    // SymbolTable table = Parser.contextTable.get(context);

    Parser.memoryStack.peek().put(ident, value);
    // if (table != null) {
    // } else {
    // table = new SymbolTable();
    // table.put(ident, value);
    // Parser.contextTable.put(context, table);
    // }
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
      if (subD != null) {
        subD.avalia();
      }
    } else if (op == TipoOperacao.FOR) {
      subE.avalia();
      while ((expr.avalia()).getBool()) {
        forCmd.avalia();
        subD.avalia();
      }
    }

    else if (op == TipoOperacao.LPARAMS) {
      if (subD != null) {
        subD.avalia();
      }
      subE.avalia();
    }

    else if (op == TipoOperacao.FUNC_BODY) {
      subE.avalia();
      if (subD != null) {
        result = subD.avalia();
      }
    }

    else if (op == TipoOperacao.PARAM) {
      // SymbolTable table = Parser.contextTable.get(Parser.contextStack.peek());

      if (Parser.memoryStack.peek().get(ident) != null) {
        // Todo: Corrigir erro
        System.out.println("Parametro ja existe");
        throw new RuntimeException("Parametro ja existe");
      }

      result = new ResultValue(0);
      addToMemory(result);
    }

    else if (op == TipoOperacao.LEXP) {
      List<ResultValue> params = new LinkedList<>();

      if (subD != null) {
        ResultValue res = subD.avalia();
        res.getParams().forEach(params::add);
      }
      ResultValue res = subE.avalia();
      params.add(res);

      result = new ResultValue(params);
    }

    else if (op == TipoOperacao.FUNC_DEF) {
      NodoNT params_body = new NodoNT(TipoOperacao.SEQ, subE, subD);
      result = new ResultValue(params_body);
      addToMemory(result);
      // Parser.contextStack.push(ident);
      // Parser.contextStack.pop();
    }

    else if (op == TipoOperacao.FUNC_CALL) {
      NodoNT params_body = Parser.memoryStack.peek().get(ident).getFunction();
      INodo params = params_body.subE;

      Parser.memoryStack.push(new HashMap<>());

      // Define valores de parametros
      params.avalia();
      ResultValue paramResult = subE.avalia();
      List<ResultValue> paramsList = paramResult.getParams();

      Object[] paramNames = Parser.memoryStack.peek().keySet().toArray();

      String currIdent = ident;
      for (int i = 0; i < paramNames.length; i++) {
        String paramName = (String) paramNames[i];
        ident = paramName;
        addToMemory(paramsList.get(i));
      }
      ident = currIdent;
      INodo body_return = params_body.subD;
      result = body_return.avalia();
      System.out.println("Result (RETURN): " + result);
      Parser.memoryStack.pop();
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
    else if (op == TipoOperacao.LPARAMS)
      result = subD + ", " + subE;
    else if (op == TipoOperacao.PARAM)
      result = " _Param_ " + ident;
    else if (op == TipoOperacao.FUNC_DEF)
      result = "def " + ident + "(" + subE + ")" + subD;
    else if (op == TipoOperacao.FUNC_CALL)
      result = ident + "(" + subE + ")";
    else if (op == TipoOperacao.FOR)
      result = "for (" + subE + ";" + expr + ";" + subD + ")" + forCmd;
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

        case SEQ:
          opBin = " SEQ ";
          break;

        default:
          opBin = " ? ";
      }
      result = "(" + subE + opBin + subD + ")";
    }
    return result;
  }

  public TipoOperacao getOperacao() {
    return op;
  }

}
