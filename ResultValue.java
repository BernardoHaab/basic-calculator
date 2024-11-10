import java.util.Arrays;
import java.util.List;

/**
 * Write a description of class ResultValue here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class ResultValue {
  private TypeEnum type;
  private double dval;
  private boolean bval;
  private NodoNT node;
  private List<ResultValue> params;

  public ResultValue(double val) {
    type = TypeEnum.DOUBLE;
    dval = val;
  }

  public ResultValue(boolean val) {
    type = TypeEnum.BOOLEAN;
    bval = val;
  }

  public ResultValue(NodoNT node) {
    type = TypeEnum.FUNCTION;
    this.node = node;
  }

  public ResultValue(List<ResultValue> params) {
    type = TypeEnum.PARAMS;
    this.params = params;
  }

  public double getDouble() {
    return dval;
  }

  public boolean getBool() {
    return bval;
  }

  public NodoNT getFunction() {
    return node;
  }

  public List<ResultValue> getParams() {
    return params;
  }

  public String toString() {
    switch (type) {
      case DOUBLE:
        return Double.toString(dval);
      case BOOLEAN:
        return Boolean.toString(bval);
      case FUNCTION:
        return node.toString();
      case PARAMS:
        return Arrays.toString(params.toArray());
    }

    return "erro! tipo nao tratado em ResultValue";
  }
}
