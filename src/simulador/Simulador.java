package simulador;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Christopher
 */
public class Simulador {
    int compAmbiente, largAmbiente, escolha, id = 1, movimentos;
    ArrayList <Objeto> objetos = new ArrayList <>();
    ArrayList <Agente> agentes = new ArrayList <>();
    
    /**
     *Define as caracteristicas do simulador.
     * @param comp comprimento do ambiente.
     * @param larg largura do ambiente.
     * @param mov numero de movimentos.
     * @param x Array de agentes a adicionar.
     * @param y Array de objetos a adicionar.
     */
    public Simulador(int comp, int larg, int mov, ArrayList <Agente> x, ArrayList <Objeto> y){
        compAmbiente=comp;
        largAmbiente=larg;
        movimentos=mov;
        agentes = x;
        objetos = y;
    }
    
    /**
     *
     */
    public Simulador(){
        
    }

    /**
     *Executa a simulaca.
     */
    public void executa(){
        Resultados resultados = new Resultados(agentes);
        
        for (Agente a: agentes){
            for (int x=0; x<movimentos; x++){
                a.setObjetos_Campo(a.objetos_campo_visao(objetos));
                a.Adiciona_Apreendidos();
                a.mover(largAmbiente, compAmbiente);
            }
            a.setObjetos_Campo(a.objetos_campo_visao(objetos));
            a.Adiciona_Apreendidos();
            escreveResultados();
        }
        resultados.executa();
    }
    
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Inicial inicio = new Inicial();
        inicio.executa();
    }
    
    /**
     *Verifica as coordenadas.
     * Caso seja objecto verifica se nas coordenadas dadas existe outro objeto e se está dentro do ambiente.
     * Casjo seja agente verifica se nas coordenadas dadas existe outro angete e se está dentro do ambiente.
     * @param x coordenadas.
     * @param tipo tipo de agente ou objeto.
     * @return booleano.
     */
    public boolean verifica_coord (int [] x, int tipo){
        int [] c = new int [2];
        if (x[0]<0 || x[0]>compAmbiente || x[1]<0 || x[1]>largAmbiente){
            popup_erro("Fora do Ambiente.\nEntidade não foi criada");
            return false;
        }
        if (tipo == 1){
            for (Objeto o: objetos){
                c = o.getCoordenadas();
                if (x[0]==c[0])
                    if (x[1]==c[1]){
                        popup_erro("Objeto já existente nessas coordenadas.\nNão foi criado objeto.");
                        return false;
                    }
            }
        }
        else if (tipo == 2){
            for (Agente a: agentes){
                c = a.getCoordenadas();
                if (x[0]==c[0])
                    if (x[1]==c[1]){
                        popup_erro("Agente já existente nessas coordenadas.\nNão foi criado agente.");
                        return false;
                    }
            }
        }
        
        return true;
    }
    
    private void popup_erro(String erro){
        JOptionPane.showMessageDialog(null, erro, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     *Carrega os dados a partir do ficheiro.
     * Cria o ambiente de acordo com os dados no ficheiro,
     * e insere os objectos apenas se nas coordenadas nao se sobreport a outro objecto.
     */
    public void carregarDados(){
        int x;
        int [] coord = new int [2];
        
        try{
            BufferedReader bf = new BufferedReader(new FileReader(new File("dados.txt")));
            String linha;
            
            // Passa o "tutorial" à frente
            for (int inicio=0; inicio<24; inicio++){
               bf.readLine();
            }
            
            // lê sempre o comprimento, a largura e o número de movimentações dos agentes
            linha = bf.readLine();
            compAmbiente = Integer.parseInt(linha.substring(25));
            linha = bf.readLine();
            largAmbiente = Integer.parseInt(linha.substring(21));
            linha = bf.readLine();
            movimentos = Integer.parseInt(linha.substring(37));
            
            //lê até ao fim do ficheiro
            while (!(linha=bf.readLine()).equals("FIM")){
                if (linha.equals("Objeto")){
                    Objeto objeto = new Objeto();
                    
                    linha = bf.readLine();
                    objeto.setCor(linha.substring(5));

                    linha = bf.readLine();
                    objeto.setForma(linha.substring(18));
                    
                    linha = bf.readLine();
                    objeto.setTipo(linha.substring(16));
                    
                    linha = bf.readLine();    
                    coord[0] = Integer.parseInt(linha.substring(3));

                    linha = bf.readLine();
                    coord[1] = Integer.parseInt(linha.substring(3));

                    //verifica se já existe um objeto nessas coordenadas e SÓ insere se não existir
                    if(verifica_coord(coord, 1)){
                        objeto.setCoordenadas(coord);
                        objeto.setIdentificador(id);
                        id++;
                        objetos.add(objeto);
                    }
                }
                else if (linha.equals("Agente")){
                    linha = bf.readLine();
                    x = Integer.parseInt(linha.substring(6));
                    switch(x){
                        case 1:                             //aleatorio
                            Aleatorio aleatorio = new Aleatorio(movimentos);
                            
                            aleatorio.setTipo(1);
                            
                            linha = bf.readLine();
                            aleatorio.setCampoVisao(Integer.parseInt(linha.substring(16)));
                            
                            linha = bf.readLine();
                            aleatorio.setCor(linha.substring(5));
                            
                            linha = bf.readLine();
                            aleatorio.setForma(linha.substring(18));
                            
                            linha = bf.readLine();    
                            coord[0] = Integer.parseInt(linha.substring(3));
                            
                            linha = bf.readLine();
                            coord[1] = Integer.parseInt(linha.substring(3));
                            
                            //verifica se já existe um agente nessas coordenadas e SÓ insere se não existir
                            if(verifica_coord(coord, 2)){
                                aleatorio.setCoordenadas(coord);
                                aleatorio.setIdentificador(id);
                                id++;
                                agentes.add(aleatorio);
                            }
                            break;
                        case 2:                             //proximo
                            Proximo proximo = new Proximo(movimentos);
                            
                            proximo.setTipo(2);
                            
                            linha = bf.readLine();
                            proximo.setCampoVisao(Integer.parseInt(linha.substring(16)));
                            
                            linha = bf.readLine();
                            proximo.setCor(linha.substring(5));
                            
                            linha = bf.readLine();
                            proximo.setForma(linha.substring(18));
                            
                            linha = bf.readLine();    
                            coord[0] = Integer.parseInt(linha.substring(3));
                            
                            linha = bf.readLine();
                            coord[1] = Integer.parseInt(linha.substring(3));
                            
                            //verifica se já existe um agente nessas coordenadas e SÓ insere se não existir
                            if(verifica_coord(coord, 2)){
                                proximo.setCoordenadas(coord);
                                proximo.setIdentificador(id);
                                id++;
                                agentes.add(proximo);
                            }
                            break;
                        case 3:                             //diferente
                            Diferente diferente = new Diferente(movimentos);
                            
                            diferente.setTipo(3);
                            
                            linha = bf.readLine();
                            diferente.setCampoVisao(Integer.parseInt(linha.substring(16)));
                            
                            linha = bf.readLine();
                            diferente.setCor(linha.substring(5));
                            
                            linha = bf.readLine();
                            diferente.setForma(linha.substring(18));
                            
                            linha = bf.readLine();    
                            coord[0] = Integer.parseInt(linha.substring(3));
                            
                            linha = bf.readLine();
                            coord[1] = Integer.parseInt(linha.substring(3));
                            
                            //verifica se já existe um agente nessas coordenadas e SÓ insere se não existir
                            if(verifica_coord(coord, 2)){
                                diferente.setCoordenadas(coord);
                                diferente.setIdentificador(id);
                                id++;
                                agentes.add(diferente);
                            }
                            break;      
                    }
                }
            }
            bf.close();
        } catch (FileNotFoundException e){
            popup_erro("Ficheiro inexistente dados.txt");
        } catch (IOException e){
            popup_erro("Excepção ao ler uma linha do ficheiro");
        }
    }
    
    /**
     * Escreve os resultados da simulação.
     */
    private void escreveResultados(){
        int num_agente = 0, num_passo = 0;
        String numero = "", linha = "";
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("Passos.txt")));
            PrintWriter pw1 = new PrintWriter(new FileWriter(new File("Objetos Vistos.txt")));
            PrintWriter pw2 = new PrintWriter(new FileWriter(new File("Objetos Visitados.txt")));
            PrintWriter pw3 = new PrintWriter(new FileWriter(new File("Estatisticas.txt")));
            
            for(Agente a:agentes){
                num_passo = 1;
                num_agente++;
                
                //Escreve em ficheiro os passos de todos os agentes, enumerando os agentes por posição na ArrayList
                pw.print("»»» "+num_agente);
                if (a.getTipo()==1) pw.println(" Agente Aleatório\n");
                else if (a.getTipo() == 2) pw.println(" Agente Próximo\n");
                else if (a.getTipo() == 3) pw.println(" Agente Máxima Diferença\n");
                for(int x=0; x<a.getPassos()[0].length; x++){
                    pw.println(num_passo+"º\tLocalização: ("+a.getPassos()[0][x]+", "+a.getPassos()[1][x]+")\n");
                    num_passo++;
                }
                pw.println();
                
                //Escreve em ficheiro todos os objetos vistos (no campo de visão) pelo agente, mostrando as suas carateristicas
                pw1.print("»»» "+num_agente);
                if (a.getTipo()==1) pw1.println(" Agente Aleatório");
                else if (a.getTipo() == 2) pw1.println(" Agente Próximo");
                else if (a.getTipo() == 3) pw1.println(" Agente Máxima Diferença");
                pw1.println();
                for(Objeto o: a.getObjetos_Apreendidos()){
                    pw1.println("ID:\t"+o.getIdentificador());
                    pw1.println("Tipo:\t"+o.getTipo());
                    pw1.println("Cor:\t"+o.getCor());
                    pw1.println("Forma:\t"+o.getForma());
                    pw1.println("Coordenadas:\t("+o.getCoordenadas()[0]+", "+o.getCoordenadas()[1]+")");
                    pw1.println();
                }
                pw1.println();
                
                // Escreve em ficheiro todos os objetos visitados, mostrando as suas carateristicas
                pw2.print("»»» "+num_agente);
                if (a.getTipo()==1) pw2.println(" Agente Aleatório\n");
                else if (a.getTipo() == 2) pw2.println(" Agente Próximo\n");
                else if (a.getTipo() == 3) pw2.println(" Agente Máxima Diferença\n");
                pw2.println();
                for(Objeto o: a.getObjetos_Visitados()){
                    pw2.println("ID:\t"+o.getIdentificador());
                    pw2.println("Tipo:\t"+o.getTipo());
                    pw2.println("Cor:\t"+o.getCor());
                    pw2.println("Forma:\t"+o.getForma());
                    pw2.println("Coordenadas:\t("+o.getCoordenadas()[0]+", "+o.getCoordenadas()[1]+")");
                    pw2.println();
                }
                pw2.println();
                
                //Escreve em ficheiro as Estatisticas pedidas
                pw3.print("»»» "+num_agente);
                if (a.getTipo()==1) pw3.println(" Agente Aleatório\n");
                else if (a.getTipo() == 2) pw3.println(" Agente Próximo\n");
                else if (a.getTipo() == 3) pw3.println(" Agente Máxima Diferença\n");
                pw3.println();
                pw3.println("Distância Percorrida: "+a.getDistanciaPercorrida());
                pw3.println("Número de Objetos Apreendidos: "+a.getObjetos_Apreendidos().size());
                pw3.println("Número de Objetos Apreendidos Diferentes: "+a.Objetos_Diferentes(a.getObjetos_Apreendidos()));
                pw3.println("Número de Objetos Visitados: "+a.getObjetos_Apreendidos().size());
                pw3.println();
                pw3.println();
            }
            pw.close();
            pw1.close();
            pw2.close();
            pw3.close();
        }catch(IOException e){
            popup_erro("Excepção ao escrever os Resultados em Ficheiro.");
        }
    }
}