
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
    if (Parser.memoryStack.peek().containsKey(sval)) {
      System.out.println("DOUBLE_VALUE: " + Parser.memoryStack.peek().get(sval).getDouble());
      return new ResultValue(Parser.memoryStack.peek().get(sval).getDouble());
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
