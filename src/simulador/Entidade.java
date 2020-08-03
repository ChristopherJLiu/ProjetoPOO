package simulador;

import java.util.*;

/**
 *
 * @author Christopher
 */
public class Entidade {

    /**
     *ID do objecto.
     */
    protected int identificador;                //identificador único de cada entidade, seja ela objeto ou agente. Irá ser iniciado em 1 e implementado sempre que se criar uma entidade nova.
    /**
     * Cor do objecto.
     */
    protected String cor;         

    /**
     *Forma do objecto.
     */
    protected String forma;            

    /**
     *Coordenadas x e y.
     */
    protected int [] coordenadas = new int[2];               //coordenadas da entidade. No caso dos agentes será atualizada sempre que se mover.

    /**
     *Define o identificador da entidade.
     * @param x Especifica o identificador da entidade.
     */
    public void setIdentificador(int x){
        identificador = x;
    }
    
    /**
     *Devolve o identificador unico da entidade.
     * @return id da entidade.
     */
    public int getIdentificador(){
        return identificador;
    }
    
    /**
     *Define a cor da entidade.
     * @param x Especifica a cor da entidade.
     */
    public void setCor(String x){
        cor = x;
    }
    
    /**
     *Devolver a cor da entidade.
     * @return cor da entidade.
     */
    public String getCor(){
        return cor;
    }
    
    /**
     *Define a forma da entidade.
     * @param x Especifica a forma da entidade.
     */
    public void setForma(String x){
        forma = x;
    }
    
    /**
     *Devolve a forma da entidade.    
     * @return forma da entidade.
     */
    public String getForma(){
        return forma;
    }
    
    /**
     *Define as coordenadas x,y da entidade.
     * @param x Especifica as coordenadas a introduzir.
     */
    public void setCoordenadas(int[] x){
        System.arraycopy(x, 0, coordenadas, 0, 2);
    }
    
    /**
     *Retorna um array com as coordenadas x e y da entidade.
     * @return coordenadas da entidade.
     */
    public int[] getCoordenadas(){
        return coordenadas;
    }
}
