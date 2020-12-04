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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lele
 */
public class ProcessadorMacros {

    BufferedReader buffRead; //reader do arquivo
    BufferedWriter buffWrite; //writer da file
    final String OUTPUT_FILE = "output.txt";

    public ProcessadorMacros(String path) throws FileNotFoundException {
        try {
            this.buffRead = new BufferedReader(new FileReader(path));//leitor do arquivo
            this.buffWrite = new BufferedWriter(new FileWriter(OUTPUT_FILE)); //escritor no arquivo de saida
        } catch (IOException ex) {
            Logger.getLogger(ProcessadorMacros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leitor() throws IOException { //Funcao lê o arquivo de entrada
        String linha = "";
        String macros[] = new String[20]; //Vetor com um número de posições arbitrárias para guardar todas as macros
        int qtMacros = 0; // quantidade de macros armazenadas no vetor
        int replaceMacro; //vai avaliar por qual macro do vetor a macro em questão tem que ser expandida

        do { //esse loop vai ler as macros e as instruções. As posições de memória vão ser lidas no outro loop
            linha = buffRead.readLine();
            if (linha == null) {//Caso aconteça algum imprevisto e a linha seja nula, sai do programa
                break;
            }
            if (linha.equals("MACRO")) {// se encontra uma macro
                macros[qtMacros] = pegaMacro(this.buffRead);//salva a macro no vetor
                qtMacros++; //aumenta a quantidade de macros
                linha = buffRead.readLine();//Depois de ler a macro, incrementa a linha
            }
            replaceMacro = linhaChamadaDeMacro(linha, macros); // a variável replaceMacro recebe a confirmação de se a linha é chamada de macro
            if (replaceMacro != 0) {//Verifica se a linha é igual a alguma chamada de macro
                buffWrite.append(macros[replaceMacro]);//Se for, adiciona a expansão da macro no buffer
            } else {
                buffWrite.append(linha + "\n");//Se não, adiciona a linha lida
            }
        } while (!linha.equals("STOP"));//Até o fim do programa executável
        buffWrite.append(linha);//Adiciona stop no buffer de escrita

        do {//loop para copar o que tem na memória do arquivo para o buffer de saída
            linha = buffRead.readLine();
            buffWrite.append(linha + "\n");
        } while (linha != null);

        buffWrite.flush();//Coloca as informações do buffer no arquivo
        buffWrite.close();//Fecha o buffer de escrita
        buffRead.close();//Fecha o buffer de leitura
    }

    public String pegaMacro(BufferedReader br) throws IOException {//Função que copia macros para a memória
        String retorno = "";//retorno da função
        String linha;//variável auxiliar de leitura
        linha = br.readLine();
        while (!linha.equals("MEND")) {//Enquando não encontrar um fim de função
            retorno += "\n" + linha;//copia o que encontrar   
            linha = br.readLine();    //Lê a próxima linha
        }
        return retorno;
    }

    public int linhaChamadaDeMacro(String linha, String[] macros) {//retorna 0 se a chamada não é igual a uma macro e retorna o numero da macro se for igual a uma macro
        if (macros[0] == null || linha.equals("*") || linha.equals("STOP")) {//O primeiro teste é se já tenho uma macro na lista
            return 0;//se não tiver, já retorna 0
        }

        String inicioLinha = linha.substring(0, linha.indexOf(' ')); //Copia até o primeiro espaço
        String inicioMacro;

        int contador = 0;
        do {//O loop vai fazer a comparação entre as strings inicioLinha e inicioMacro, até encontrar uma macro, ou o a string inicio macro ser nula, ou seja, não ter nada dentro dela
            if (macros[contador] == null) {
                contador = 0;
                break;
            }
            inicioMacro = macros[contador].substring(1, macros[contador].indexOf(" "));//Copia até o primeiro espaço. O íncide precisa ser 1 porque os macros tem como primeiro caractere um /n

            if (inicioMacro.startsWith("&")) {//se a primeira palavra encontrada na macro for um rótulo
                int inicio = macros[contador].indexOf(" ") + 1;//pula o label e pega depois do primeiro espaço
                int fim = macros[contador].indexOf(" ", inicio);
                inicioMacro = macros[contador].substring(inicio, fim);//atualiza o valor de inicioMacro para o próximo valor depois do label
            }
            contador++;//Autualiza o contador
        } while (!inicioLinha.equals(inicioMacro));//O loop encerra quando inicioLinha for igual a inicioMacro, ou quando a posição do vetor de macros for nula(if)

        return contador;//retorna o valor de contador (será zero se a linha não corresponder a uma chamada de macro, ou o número da macro no vetor se ela corresponder)
    }
}
