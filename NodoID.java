
/**
 * Write a description of class Nodo here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NodoID implements INodo {
  private int tipo;
  private String sval;

  public NodoID(String valor) {
    sval = valor;
  }

  public ResultValue avalia() {
    String context = Parser.contextStack.peek();
    SymbolTable table = Parser.contextTable.get(context);

    System.out.println("NodoID: " + sval + " - " + table);

    if (table.contains(sval)) {
      System.out.println("DOUBLE_VALUE: " + table.get(sval).getDouble());
      return new ResultValue(table.get(sval).getDouble());
    } else
      return new ResultValue(0);
  }

  public TipoOperacao getOperacao() {
    return null;
  }

  public String toString() {
    return sval;
  }
}
