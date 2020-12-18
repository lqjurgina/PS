/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author darle
 */
public class Ligador {
    BufferedReader buffRead1,buffRead2;
    BufferedWriter buffWrite;
    final String OUTPUT_FILE = "src/arquivos/cod.hpx.txt";
    ArrayList<String> cod = new ArrayList ();
    Map<String, Integer> tabela = new HashMap<String, Integer>();
    int tamanho;
    
    public Ligador(String path1, String path2) throws FileNotFoundException, IOException {
        System.err.println(path1);
        System.err.println(path2);
        this.buffRead1 = new BufferedReader(new FileReader(path1));//leitor do arquivo
        this.buffRead2 = new BufferedReader(new FileReader(path2));
        this.buffWrite = new BufferedWriter(new FileWriter(OUTPUT_FILE)); //Escritor no arquivo de saída
    }
    
    public void primeiraPassagemLigador() throws IOException{ //Cria a tabela Global e junta os dois códigos em um arraylist
        String linha1 = buffRead1.readLine();
        String[] aux;
        int aux2;
        String aux3;
        
        System.out.println("OBJ1");
        while (!linha1.equals("TABELA")){  //Lê todo código até chegar na tabela
            if(!linha1.startsWith("*")){ //Condição para ignorar comentários do código
            cod.add(linha1); //Armazena o código em um arraylist
            }
            System.out.println(linha1);
            linha1 = buffRead1.readLine();
        }
        
        linha1 = buffRead1.readLine(); //Pula linha TABELA
        
        while (!linha1.equals("TAMANHO")){
            aux = linha1.split(" "); //Quebra a linha a partir do ESPAÇO
            aux2 = Integer.parseInt(aux[1]); //Transforma o endereço string para INT
            tabela.put(aux[0], aux2); //Armazena a tabela global em um HashMap
            System.out.println(linha1);
            linha1 = buffRead1.readLine();
        }
        
        linha1 = buffRead1.readLine(); //Pula linha TAMANHO
        aux3 = linha1;
        tamanho = Integer.parseInt(aux3); //Guarda o tamanho do módulo 1
        
        System.out.println("==================");
        String linha2 = buffRead2.readLine();
        
        System.out.println("OBJ2");
        while (!linha2.equals("TABELA")){ //Neste laço já é feita a junção dos códigos dentro do arraylist
            if(!linha2.startsWith("*")){
            cod.add(linha2);
            }
            System.out.println(linha2);
            linha2 = buffRead2.readLine();
        }
        linha2 = buffRead2.readLine(); //Pula a linha TABELA
        while (!linha2.equals("TAMANHO")){
            aux = linha2.split(" "); //Quebra a linha a partir do ESPAÇO
            aux2 = Integer.parseInt(aux[1]); //Transforma o endereço string para INT
            aux2+= tamanho; //Aqui é calculada o novo endereço fazendo a soma com o tamanho do módulo 1
            tabela.put(aux[0], aux2);
            System.out.println(linha2);
            linha2 = buffRead2.readLine();
        }
        System.out.println("==================");
        System.out.println("Tamanho Módulo 1");
        System.out.println(tamanho);
        System.out.println("==================");
        System.out.println("Código Concatenado");
        System.out.println(cod);
        System.out.println("==================");
        System.out.println("TABELA GLOBAL");
        System.out.println(tabela);
    }
        
        public void segundaPassagemLigador() throws IOException{ //Armazena o arraylist que contém o código em um arquivo .HPX trocando os LABELS pelo endereço correspondente
            String aux;
            int aux2;
            
            for(int i=0;i<cod.size();i++){
                aux = cod.get(i);
                
                if(!aux.startsWith("0")){ //Detecta se foi encontrado um LABEL
                    aux2 = tabela.get(aux); //Pega o valor do endereço do LABEL na tabela global
                    aux2+= 12; //Soma o valor do endereço com o tamanho da pilha
                    aux = FuncoesUteis.intToBinaryString(aux2, 16); //Transforma o endereço para binário
                    buffWrite.append(aux+ "\n"); //Escreve no arquivo .HPX
                }else {
                buffWrite.append(aux+ "\n"); //Caso não seja um LABEL, é escrito diretamente no .HPX
                }
            }
            buffWrite.flush();//Coloca as informações do buffer no arquivo
            buffWrite.close();//Fecha o buffer de escrita
            buffRead1.close();//Fecha o buffer de leitura
            buffRead2.close();//Fecha o buffer de leitura
    }
}
