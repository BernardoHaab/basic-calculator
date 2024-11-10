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

  public boolean contains(String key) {
    return table.containsKey(key);
  }

  public HashMap<String, ResultValue> getTable() {
    return table;
  }

  @Override
  public String toString() {
    return table.toString();
  }

}
