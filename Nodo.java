
/**
 * Write a description of class Nodo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Nodo
{
    private int tipo;
    private double dval;
    private boolean bval;
    private int op;
    private Nodo subE, subD;
    
    public Nodo(int op, Nodo se, Nodo sd) {
        tipo = TipoNodo.NaoTerminal;
        this.op = op;
        subE = se;
        subD = sd;
    }

    public Nodo(double valor) {
        tipo = TipoNodo.Terminal;
        dval = valor;
        subE = null;
        subD = null;
    }
    
    public double avalia() {
    
        double result;
        
        if (tipo == TipoNodo.Terminal)
            result = dval;
        else {
            switch (op) {
                case TipoOperacao.ADD:
                    result = subE.avalia() + subD.avalia();
                    break;
                 case TipoOperacao.MULL:
                    result = subE.avalia() * subD.avalia();
                    break;
                 default:
                    result = 0;
                }
            }
        
            return result;
                       
    }
    
    public String toString() {
        String result;
        
        if (tipo == TipoNodo.Terminal)
            result = Double.toString(dval);
        else {
            switch (op) {
                case TipoOperacao.ADD:
                    result = "(" + subE + " + " + subD+")";
                    break;
                 case TipoOperacao.MULL:
                    result = "(" + subE + " * " + subD +")";                    break;
                 default:
                    result = "?";
                }
            }
        
            return result;
        }       

}
