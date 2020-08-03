package simulador;

import java.util.*;

/**
 *
 * @author Christopher
 */
public class Diferente extends Agente{

    /**
     * Define numero de passos.
     * @param x numero de passos.
     */
    public Diferente(int x){
        passos = new int[2][x];
    }
    

    @Override
    public void mover(int largAmbiente, int compAmbiente){
        ArrayList <Objeto> sem_repeticao = new ArrayList<>();
        sem_repeticao = campo_visao_sem_repeticao();
        Random gera = new Random();
        int tamanho,posicao=0;
        int [] solo = new int[2];
        int Hamming,Hamming_menor,diferenca=0;
        String cor,forma,tipo;
        tamanho = sem_repeticao.size();
        
        if (tamanho == 0){      //se não tiver nenhum objeto no campo de visão
            int [] c = new int[2];
            do{
                //anda 1 casa para qualquer direção
                c[0] = gera.nextInt(3) - 1;
                c[1] = gera.nextInt(3) - 1;
            } while (c[0]>largAmbiente || c[0]<0 || c[1]>compAmbiente || c[1]<0 || (coordenadas[0]==c[0] && coordenadas[1]==c[1]));         // verifica se não sai dos limites do ambiente
            distancia_percorrida += distancia(coordenadas, c);      //adiciona a distância entre as coordenadas atuais e as para onde vai saltar
            coordenadas[0] = c[0];
            coordenadas[1] = c[1];
        }
        else{
            for(int i=0;i<tamanho;i++){
                cor=sem_repeticao.get(i).getCor();
                tipo=sem_repeticao.get(i).getTipo();
                forma=sem_repeticao.get(i).getForma();
                Hamming=0;
                Hamming_menor=4;
                for (Objeto o : objetos_visitados) {
                    if (cor.equals(o.getCor())) {
                        Hamming++;
                    }
                    if (tipo.equals(o.getTipo())) {
                        Hamming++;
                    }
                    if (forma.equals(o.getForma())) {
                        Hamming++;
                    }
                    if(Hamming<Hamming_menor)
                        Hamming_menor=Hamming;
                }
                if(Hamming_menor>diferenca){
                    diferenca=Hamming_menor;
                    posicao=i;
                }    
            }
        
            distancia_percorrida += distancia(coordenadas, sem_repeticao.get(posicao).getCoordenadas());    //adiciona a distancia entre as coordenadas atuais e as do objeto na posicao random da ArrayList
            //copia as coordenadas do objeto escolhido aleatoriamente para as coordenadas do Agente (o agente fica na mesma posição do objeto)
            System.arraycopy(sem_repeticao.get(posicao).getCoordenadas(), 0, coordenadas, 0, 2);    
            //adiciona o objeto visitado ao arrayList
            objetos_visitados.add(sem_repeticao.get(posicao));  
        } 
        passos(coordenadas);
    }
}