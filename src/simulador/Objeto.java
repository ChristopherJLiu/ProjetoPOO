package simulador;

import java.util.*;

/**
 *
 * @author Christopher
 */
public class Objeto extends Entidade{
    private String tipo; // cadeira, mesa, etc.
    
    /**
     *Define o tipo de objeto.
     * @param x tipo de objeto.
     */
    public void setTipo(String x){
        tipo = x;
    }
    
    /**
     *Devolve tipo de objeto.
     * @return tipo de objeto.
     */
    public String getTipo(){
        return tipo;
    }
}
