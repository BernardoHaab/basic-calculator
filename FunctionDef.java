public class FunctionDef {

  private String name;
  private NodoNT body;
  private NodoNT params;

  public FunctionDef(String name, NodoNT params, NodoNT body) {
    this.name = name;
    this.params = params;
    this.body = body;
  }

  public String getName() {
    return name;
  }

  public NodoNT getParams() {
    return params;
  }

  public NodoNT getBody() {
    return body;
  }

  public String toString() {
    return "function " + name + "(" + params + ") " + body;
  }

}
