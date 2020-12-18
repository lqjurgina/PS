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
import java.util.HashMap;

/**
 *
 * @author lele
 */
public class ProcessadorMacros {

    BufferedReader buffRead; //reader do arquivo
    BufferedWriter buffWrite; //writer da file

    public ProcessadorMacros(String path, String saida) throws FileNotFoundException {
        try {
            this.buffRead = new BufferedReader(new FileReader(path));//leitor do arquivo
            this.buffWrite = new BufferedWriter(new FileWriter(saida)); //escritor no arquivo de saida
        } catch (IOException ex) {
            Logger.getLogger(ProcessadorMacros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leitor() throws IOException { //Funcao lê o arquivo de entrada
        String linha;
        buffWrite.append("CR");//adicionado pra dar match na formatação de exemplo desenvolvida na planilha
        HashMap<String,String> macros = new HashMap<>(); //hashmap para guardar todas as macros
        String replaceMacro; //Auxiliar, caso haja uma chamada de macro
        linha = buffRead.readLine();
        while (linha != null) { // le o arquivo inteiro
            linha = linha.trim();
            if (linha.contains("MACRO")) {// se encontra uma definiçao de macro
                pegaMacro(macros);//abre o modo de definição, salva a macro no hashmap
            }
            else { 
                replaceMacro = linhaChamadaDeMacro(linha, macros); //se a linha é chamada de macro a variável replaceMacro recebe a definicao da macro que foi chamada
                if (replaceMacro != null) {//Verifica se a linha é igual a alguma chamada de macro
                    String macroCorrigida = trocaParametros(linha, replaceMacro, macros);//Se é, vai trocar os parâmetros posicionais pelos da chamada e retirar o cabecalho da chamada
                    buffWrite.append(macroCorrigida);//Então adiciona a expansão da macro no buffer.
                } else {
                    buffWrite.append(linha + "\n");//Se não, adiciona a linha lida
                }
            }
            linha = buffRead.readLine();
        }
        //for (String nome : macros.keySet()) System.out.println(nome); //debug - imprime o nome das macros definidas
        buffWrite.append("LF");
        buffWrite.flush();//Coloca as informações do buffer no arquivo
        buffWrite.close();//Fecha o buffer de escrita
        buffRead.close();//Fecha o buffer de leitura
    }
    
    // "modo de definição"
    private void pegaMacro(HashMap<String,String> macros) throws IOException {//Função que copia macros para a memória
        String retorno = "";//retorno da função
        String linha;//variável auxiliar de leitura
        String nome; //guarda o nome da macro
        String replaceMacro; //auxiliar, caso haja uma chamada de macro dentro da definicao dessa macro
        int macrosAninhadas = 0; //contador de macros aninhadas

        linha = this.buffRead.readLine();
        linha = linha.trim();
        String[] parametros = linha.split("[ ,]");
        if (parametros[0].startsWith("&")) { // primeiro parametro é um label, entao o segundo vai ser o nome
            nome = parametros[1];
        }
        else nome = parametros[0];
        //System.out.println(nome); //debug
        while (!(linha.contains("MEND") && macrosAninhadas == 0)) {//Enquanto não encontrar o fim da macro
            if      (linha.contains("MEND"))   macrosAninhadas -= 1;
            else if (linha.contains("MACRO"))  macrosAninhadas += 1;
            // teste de chamada de macro dentro da macro
            replaceMacro = linhaChamadaDeMacro(linha, macros); //se a linha é chamada de macro a variável replaceMacro recebe a definicao da macro que foi chamada
            if (replaceMacro != null) {//Verifica se a linha é igual a alguma chamada de macro
                String macroCorrigida = trocaParametros(linha, replaceMacro, macros);//Se é, vai trocar os parâmetros posicionais pelos da chamada e retirar o cabecalho da chamada
                retorno += macroCorrigida;//Então adiciona a expansão da macro no buffer.
            } else {
                retorno += linha + "\n";//Se não houve chamada, adiciona a linha lida
            }
            linha = this.buffRead.readLine();    //Lê a próxima linha
            linha = linha.trim();
        }
        macros.put(nome, retorno);
    }
    
    // Retorna a macro se for uma chamada de macro, ou null se não for
    private String linhaChamadaDeMacro(String linha, HashMap<String,String> macros) {
        if (macros.isEmpty() || linha.startsWith("*") || linha.contains("STOP") || linha.contains("END") || linha.equals("\n")) { //O primeiro teste é se já tenho uma macro na lista
            return null; //se não tiver, já retorna null
        }
        String nome;
        String[] parametrosChamada = linha.split("[ ,]");
        //System.out.println(linha); //debug
        nome = parametrosChamada[0];
        if (parametrosChamada.length > 1 && macros.get(nome) == null) // se houver outro parametro e o nome nao funcionar, pode ser que tenha sido uma label
            nome = parametrosChamada[1]; // entao tenta com o proximo parametro

        return macros.get(nome); //retorna a macro correspondente a "nome", se existir (caso contrário retorna null)
    }
    
    // "modo de expansão"
    private String trocaParametros(String chamadaDaMacro, String macroInteira, HashMap<String,String> macros) { 
        String saida = "";
        String replaceMacro;
        String label = "";
        String primeiraLinha = macroInteira.substring(0, macroInteira.indexOf("\n")); // primeira linha da macro
        String[] parametrosDaMacroComNome = primeiraLinha.split("[ ,]");  // lista dos parametros da macro + nome
        String[] parametrosDaChamadaComNome = chamadaDaMacro.split("[ ,]"); // lista dos parametros da chamada + nome
        if (!(parametrosDaMacroComNome[0].equals(parametrosDaChamadaComNome[0]) || parametrosDaMacroComNome[1].equals(parametrosDaChamadaComNome[1]))
                && parametrosDaMacroComNome[0].charAt(0) != '&') { // tem uma label nao usada na chamada
            label = parametrosDaChamadaComNome[0] + " ";
            parametrosDaChamadaComNome = chamadaDaMacro.trim().substring(chamadaDaMacro.indexOf(" ") + 1).split("[ ,]"); // ignora a label
        }
        String chamadaDeMacroCorrigida = macroInteira; //Cria uma cópia da definição da macro pra poder fazer as alterações
        
        for (int a = 0; a < parametrosDaChamadaComNome.length; a++) {//esse loop substitui todas as ocorrências de um parâmetro posicional por um parâmetro real
            // funciona sem tirar o nome pois o nome sempre vai estar na mesma posicao, então nao muda nada
            chamadaDeMacroCorrigida = chamadaDeMacroCorrigida.replaceAll(parametrosDaMacroComNome[a], parametrosDaChamadaComNome[a]);
        }
        //Neste ponto, as variáveis da macro já estão corrigidas.
        //Agora verificamos a existencia de macros aninhadas
        chamadaDeMacroCorrigida = chamadaDeMacroCorrigida.substring(chamadaDeMacroCorrigida.indexOf("\n", 1)+1);//Pega a substring sem o cabeçalho da função, ou seja a primeira occorência de \n (primeiro enter depois do caractere 1, que também é um \n)
        chamadaDeMacroCorrigida = label + chamadaDeMacroCorrigida; //aplica a label ao inicio da macro nova, se existir
        String[] macroNova = chamadaDeMacroCorrigida.split("\n");
        for (int i = 0; i < macroNova.length; i++) {
            if (macroNova[i].contains("MACRO")) {// se encontra uma macro
                i = pegaMacroString(macroNova, i+1, macros);//salva a macro no hashmap
            }
            else { 
                replaceMacro = linhaChamadaDeMacro(macroNova[i], macros); //se a linha é chamada de macro a variável replaceMacro recebe a definicao da macro que foi chamada
                if (replaceMacro != null) {//Verifica se a linha é igual a alguma chamada de macro
                    saida += trocaParametros(macroNova[i], replaceMacro, macros);//Vai trocar os parâmetros posicionais pelos da chamada e retirar o cabecalho da chamada
                } else {
                    //System.out.println(linha); //debug
                    saida += macroNova[i] + "\n";
                }
            }
        }
        return saida;
    }
    
    // faz o mesmo que pegaMacro, porém em uma array de strings
    private int pegaMacroString(String[] macroString, int indice, HashMap<String,String> macros) {
        String macro = "";//macro a ser lida
        String nome; //guarda o nome da macro
        String replaceMacro;
        int macrosAninhadas = 0; //contador de macros aninhadas
       
        String[] parametros = macroString[indice].split("[ ,]");
        if (parametros[0].startsWith("&")) { // primeiro parametro é um label, entao o segundo vai ser o nome
            nome = parametros[1];
        }
        else nome = parametros[0];
        //System.out.println(nome); //debug
        while (!(macroString[indice].contains("MEND") && macrosAninhadas == 0)) {//Enquanto não encontrar o fim da macro
            if      (macroString[indice].contains("MEND"))   macrosAninhadas -= 1;
            else if (macroString[indice].contains("MACRO"))  macrosAninhadas += 1;
            // teste de macro dentro da macro
            replaceMacro = linhaChamadaDeMacro(macroString[indice], macros); //se a linha é chamada de macro a variável replaceMacro recebe a definicao da macro que foi chamada
            if (replaceMacro != null) {//Verifica se a linha é igual a alguma chamada de macro
                String macroCorrigida = trocaParametros(macroString[indice], replaceMacro, macros);//Vai trocar os parâmetros posicionais pelos da chamada e retirar o cabecalho da chamada
                //verificaMacrosAninhadas(macroCorrigida, macros);//essa função tem que ser implementada
                macro += macroCorrigida;//Se for, adiciona a expansão da macro no buffer. Aqui é usado replaceMacro-1 porque
            } else {
                //System.out.println(linha); //debug
                macro += macroString[indice] + "\n";//Se não, adiciona a linha lida
            }
            indice++;
        }
        macros.put(nome, macro);
        return indice;
    }

}
