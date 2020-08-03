package simulador;

import java.util.*;

/**
 *
 * @author Christopher
 */
public class Agente extends Entidade{
    /**
     * Tipo de agente.
     */
    protected int tipo;

    /**
     *Campo de visao do agente.
     */
    protected int campovisao;

    /**
     *Numero de passos a dar pelo agente.
     */
    protected int n_passos = 0;

    /**
     *Passos a seguir pelo agente.
     */
    protected int [][]passos;

    /**
     *Distancia total percorrida pelo agente.
     */
    protected double distancia_percorrida=0;                                                //distancia total percorrida

    /**
     *Objectos por onde o agente passou.
     */
    protected ArrayList <Objeto> objetos_apreendidos = new ArrayList <>();                  //diferentes vistos

    /**
     *Objectos que ja estiveram no campo de visao do agente
     */
    protected ArrayList <Objeto> objetos_visitados = new ArrayList <>();                    //todos os objetos vistos

    /**
     *Objectos no campo de visao do agente.
     */
    protected ArrayList <Objeto> objetos_campo = new ArrayList<>();
    
    //move o agente para o objeto

    /**
     *Estrategia de movimento que o agente utiliza.
     * @param largAmbiente largura  
     * @param compAmbiente comprimento
     */
        public void mover(int largAmbiente, int compAmbiente){
       
    }
    
    /**
     *Adiciona as novas coordenadas para onde o agente vai.
     * @param x coordenadas novas.
     */
    protected void passos(int []x){
        passos[0][n_passos] = x[0];
        passos[1][n_passos] = x[1];
        n_passos++;
    }
    
    //distancia entre duas entidades

    /**
     *Calcula a distancia entre 2 entidades.
     * @param x valor das coordenadas x e y
     * @param y valor da coordenadas x e y
     * @return distancia
     */
        protected double distancia(int [] x, int [] y){
        return Math.sqrt(Math.pow((x[1]-y[1]), 2.0)+Math.pow((x[0]-y[0]), 2));
    }
        
    //devolve objetos no campo de visao do agente
    //pede a lista de todos os objetos no ambiente

    /**
     *Define os objetos  no campo de visao
     * @param objetos objetos de onde vai adicionar para o campo de visao
     * @return objetos no campo de visao
     */
        public ArrayList<Objeto> objetos_campo_visao(ArrayList <Objeto> objetos){
        ArrayList <Objeto> objetos_visao = new ArrayList<>();
        for (Objeto x : objetos) {
            if (distancia(coordenadas, x.coordenadas)<=campovisao){     //verifica se as coordenadas do objeto estão dentro do campo de visão
                objetos_visao.add(x);               //adiciona à lista de objetos no campo de visao que vai ser devolvida pela função
            }
        }
        return objetos_visao;
    }
    
    /**
     *Devolve os objetos no {@link objetos_campo} sem os repetidos
     * Percorrendo os objectos no campo de visao, caso o id de algum dos objetos estiver em {@link objetos_visitados}
     * nao adiciona ao novo array
     * @return ArrayList de objetos sem os por onde ja passou
     */
    protected ArrayList<Objeto> campo_visao_sem_repeticao(){
        ArrayList <Objeto> sem_repetidos = new ArrayList <>();          //lista a enviar sem objetos que já tenham sido visitados
        int verifica;                                                   //variável de controlo
        for (Objeto o: objetos_campo){
            verifica = 0;                                               //reinicializa a variável sempre que vai para um objeto diferente do campo de visão
            for (Objeto v: objetos_visitados){
                
                 //se o Identificador, que é único para cada objeto, for igual, a variável de controlo muda e passa ao objeto do campo de visão seguinte
                if (v.getIdentificador()==o.getIdentificador()){       
                    verifica = 1;
                    break;
                }
            }
            if (verifica == 0){                         //se a variável de controlo continuar inalterada adicina o objeto à lista
                sem_repetidos.add(o);
            }
        }
        return sem_repetidos;
    }
    
    /**
     *Verifica dentro do array x quantos objectos sao diferentes.
     * @param x Arraylist de objetos a verificar
     * @return numero de objetos diferentes
     */
    public int Objetos_Diferentes(ArrayList <Objeto> x){
        int contador = 0, verifica = 0;
        for (int a=0; a<x.size(); a++){
            verifica = 0;
            for (int b=0; b<x.size(); b++){
                if (x.get(a).getCor().equals(x.get(b).getCor()) && x.get(a).getTipo().equals(x.get(b).getTipo()) && x.get(a).getForma().equals(x.get(b).getForma()))
                    verifica++;
            }
            if (verifica == 1) contador++;
        }
        return contador;
    }
    
     /**
     *Adiciona ao objetos_aprendidos os objecto que estao no seu campo de visao.
     * 
     * Usando um ciclo que percorre os objtos_campo e outro ciclo percorrendo{@link objetos_apreendidos}
     * testa se os seus ids sao iguais caso sejam o valor de teste passa a 1 caso nao contrario mantem a 0,
     * caso a variavavel teste esteja a 0 adciona o objecto em objetos_apreendidos.
     */
    public void Adiciona_Apreendidos(){
        int testa;
        for(Objeto i: objetos_campo){//Ciclo que percorre os objectos no campo de visao
            testa=0;
            for(Objeto u: objetos_apreendidos){//Percorre objectos aprendidos (que ja estiverem no seu campo de visao)
                if(u.identificador==i.identificador){//Se ele existir adiciona 1 ao teste
                    testa=1;
                    break;  //se ele encontrar passa para o objeto no campo de visão seguinte
                }
            }
            if(testa==0){//Caso o teste esteja a 0 depois d percorrer os objectos aprendidos
                objetos_apreendidos.add(i);//Adiciona o objecto i nos objectos aprendidos
            }
        }
    }
   
    /**
     *Define tipo de agente.
     * @param x Especifica o tipo de agente.
     */
    public void setTipo(int x){
        tipo = x;
    }
    
    /**
     *Devolve o tipo de agente.
     * @return tipo de agente.
     */
    public int getTipo(){
        return tipo;
    }
    
    /**
     *Define o campo de visao do agente.
     * @param x Especifica o campo de visao num valor inteiro.
     */
    public void setCampoVisao(int x){
        campovisao = x;
    }
    
    /**
     *Devolve o campo de visao do agente.
     * @return valor inteiro, que e o campo de visao.
     */
    public int getCampoVisao(){
        return campovisao;
    }
    
    /**
     *Devolve a distancia ja percorrida pelo agente.
     * @return distancia percorrida pelo agente.
     */
    public double getDistanciaPercorrida(){
        return distancia_percorrida;
    }
    
    /**
     *Adiciona a {@link objetos_campo} cada objeto na Arraylist x
     * @param x Arraylist de objetos a adicionar
     */
    public void setObjetos_Campo(ArrayList <Objeto> x){
        objetos_campo.clear();
        for (Objeto o : x){
            objetos_campo.add(o);
        }
    }
    
    /**
     *Devolve um array com os objectos por onde ja passou.
     * @return array com os objetos por onde ja passou.
     */
    public ArrayList <Objeto> getObjetos_Visitados(){
        return objetos_visitados;
    }
    
    /**
     *Devolve um array com os objectos que ja estiverem no seu campo de visao sem estarem repetidos.
     * @return array com os objectos que ja estiveram no seu campo de visao.
     */
    public ArrayList <Objeto> getObjetos_Apreendidos(){
        return objetos_apreendidos;
    }
    
    /**
     *Devolve tabela bidimensional com as coordenadas.
     * @return tabela bidimensional
     */
    public int[][] getPassos(){
        return passos;
    }
    // Distancia percorrida não precisa de setter porque é iniciada em 0 e não é um valor pre-definido
}
