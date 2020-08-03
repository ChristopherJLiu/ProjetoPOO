package simulador;

import java.util.*;

/**
 *
 * @author Christopher
 */
public class Proximo extends Agente{

    /**
     *Define numero de passos do agente.
     * @param x numero de passos.
     */
    public Proximo(int x){
        passos = new int[2][x];
    }
    
    //move para o objeto mais próximo do agente. Se houver 2 objetos à mesma distância o agente vai para o que tiver visto primeiro entre esses 2.
    @Override
    public void mover(int largAmbiente, int compAmbiente){
        ArrayList <Objeto> sem_repeticao = new ArrayList<>();
        sem_repeticao = campo_visao_sem_repeticao();
        Random gera = new Random();
        int tamanho=sem_repeticao.size();
        double maisproximo = campovisao + 1;            // estando no campo de visao +1, vai estar sempre mais longe do que qualquer objeto no campo de visao
        double dist;
        int posicao=0;                               //posicao do objecto no array
        
        if (tamanho == 0){                //se não tiver nenhum objeto no campo de visão
            int [] c = new int[2];
            do{
                //anda 1 casa para qualquer direção
                c[0] = gera.nextInt(3) - 1;
                c[1] = gera.nextInt(3) - 1;
            } while (c[0]>largAmbiente || c[0]<0 || c[1]>compAmbiente || c[1]<0 || (coordenadas[0]==c[0] && coordenadas[1]==c[1]));            // verifica se não sai dos limites do ambiente
            distancia_percorrida += distancia(coordenadas, c);              //adiciona a distância entre as coordenadas atuais e as para onde vai saltar
            coordenadas[0] = c[0];
            coordenadas[1] = c[1];
        }
        else{
            for(int i=0;i<tamanho;i++){                 //calculas a distancia de cada objecto;
                dist = distancia(coordenadas, sem_repeticao.get(i).getCoordenadas());
                if (dist < maisproximo){
                    maisproximo = dist;
                    posicao = i;
                }
            }
            
            distancia_percorrida += maisproximo;
            
            //copia as coordenadas do objeto mais próximo para as coordenadas do Agente (o agente fica na mesma posição do objeto)
            System.arraycopy(sem_repeticao.get(posicao).getCoordenadas(), 0, coordenadas, 0, 2);    
            //adiciona o objeto visitado ao arrayList
            objetos_visitados.add(sem_repeticao.get(posicao));  
        }
        passos(coordenadas);
    }
}