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
            if (replaceMacro > -1) {//Verifica se a linha é igual a alguma chamada de macro
                String macroCorrigida = trocaParametros(linha, macros[replaceMacro]);//Vai trocar os parâmetros posicionais pelos da chamada e retiriar o cabecalho da chamada
                verificaMacrosAninhadas(macroCorrigida, macros);//essa função tem que ser implementada
                buffWrite.append(macroCorrigida);//Se for, adiciona a expansão da macro no buffer. Aqui é usado replaceMacro-1 porque
            } else if (replaceMacro == -1) {
                buffWrite.append("\n" + linha);//Se não, adiciona a linha lida
            }
        } while (!linha.equals("STOP"));//Até o fim do programa executável

        buffWrite.append("\n");
        linha = buffRead.readLine(); // atualiza o valor de linha pra entrada do loop não ser com o STOP duplicado
        while (linha != null) {//loop para copar o que tem na memória do arquivo para o buffer de saída
            buffWrite.append(linha + "\n");
            linha = buffRead.readLine();
        }

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

    public int linhaChamadaDeMacro(String linha, String[] macros) {//retorna um valor <0 se a chamada não é igual a uma macro e retorna o numero da macro se for igual a uma macro
        if (macros[0] == null || linha.startsWith("*") || linha.equals("STOP") || linha.equals("\n")) {//O primeiro teste é se já tenho uma macro na lista
            return -1;//se não tiver, já retorna -1
        }

        //System.out.println(linha);//debug
        String inicioLinha = linha.substring(0, linha.indexOf(' ')); //Copia até o primeiro espaço
        String inicioMacro;

        int contador = 0;
        do {//O loop vai fazer a comparação entre as strings inicioLinha e inicioMacro, até encontrar uma macro, ou o a string inicio macro ser nula, ou seja, não ter nada dentro dela
            if (macros[contador] == null) {
                contador = -1;
                break;
            }
            inicioMacro = macros[contador].substring(1, macros[contador].indexOf(" "));//Copia até o primeiro espaço. O íncide precisa ser 1 porque os macros tem como primeiro caractere um /n

            if (inicioMacro.startsWith("&")) {//se a primeira palavra encontrada na macro for um rótulo
                int inicio = macros[contador].indexOf(" ") + 1;//pula o label e pega depois do primeiro espaço
                int fim = macros[contador].indexOf(" ", inicio);
                inicioMacro = macros[contador].substring(inicio, fim);//atualiza o valor de inicioMacro para o próximo valor depois do label
            }
            contador++;//Autualiza o contador
            // System.out.println(inicioLinha + " " + inicioMacro + " " + inicioLinha.equals(inicioMacro) + " " + contador); //debug
        } while (!inicioLinha.equals(inicioMacro));//O loop encerra quando inicioLinha for igual a inicioMacro, ou quando a posição do vetor de macros for nula(if)

        return contador - 1;//retorna o valor de contador (será -1 se a linha não corresponder a uma chamada de macro, ou o número da macro no vetor se ela corresponder)
    }

    String trocaParametros(String chamadaDaMacro, String macroInteira) {
        String parametrosDaMacro = macroInteira.substring(macroInteira.indexOf(" "), macroInteira.indexOf("\n", 1));//Pega na primeira linha da definição da macro os parâmetros(compreendidos entre o primeiro espaço e o \n
        String parametrosDaChamada = chamadaDaMacro.substring(chamadaDaMacro.indexOf(" "));//Pega os parâmetros da chamada da macro
        String[] parametrosDaMacroIndividuais = parametrosDaMacro.split(",");//divide a string nas virgulas, criando um vetor com os parametros separados
        String[] parametrosDaChamadaIndividuais = parametrosDaChamada.split(",");//divide a string nas virgulas, criando um vetor com os parametros separados
        String chamadaDeMacroCorrigida = macroInteira; //Cria uma cópia da definição da macro pra poder fazer as alterações
        for (int a = 0; a < parametrosDaChamadaIndividuais.length; a++) {//esse loop substitui todas as ocorrências de um parâmetro posicional por um parâmetro real
            chamadaDeMacroCorrigida = chamadaDeMacroCorrigida.replaceAll(parametrosDaMacroIndividuais[a], parametrosDaChamadaIndividuais[a]);
        }
        //Neste ponto, as variáveis da macro já estão corrigidas.
        return chamadaDeMacroCorrigida.substring(chamadaDeMacroCorrigida.indexOf("\n", 1));//Pega a substring sem o cabeçalho da função, ou seja a primeira occorência de \n (primeiro enter depois do caractere 1, que também é um \n)
    }

    void verificaMacrosAninhadas(String linhas, String[] macros) {
    }
}
