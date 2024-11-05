import java.util.HashMap;

public class SymbolTable {
  private HashMap<String, ResultValue> table;

  public SymbolTable() {
    table = new HashMap<String, ResultValue>();
  }

  public void put(String key, ResultValue value) {
    table.put(key, value);
  }

  public ResultValue get(String key) {
    return table.get(key);
  }

}
