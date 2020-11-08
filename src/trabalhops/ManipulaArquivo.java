/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author darle
 */
public class ManipulaArquivo {

    private String path; //caminho para o arquivo de input
    private Memoria memoria; //memoria
    BufferedReader buffRead; //reader do arquivo
    int local; // posiçao de memoria onde a varivel lida fica armazenada

    public ManipulaArquivo(String path, Memoria memoria) throws FileNotFoundException {
        this.path = path;
        this.memoria = memoria;
        this.buffRead = new BufferedReader(new FileReader(this.path));
        local = memoria.getINICIO_INS_DADOS();//inicia o armazenamento de dados no inicio da memoria
    }

    public boolean leitor() { //Funcao retorna true enquanto houverem coisas para serem lidas no arquivo, e falso depois da leitura da file
        String linha; // variavel temporaria para guardar o que esta sendo lido do arquivo
        try { //tenta ler o arquivo, se nao conseguir, apenas retorna no catch.
            linha = buffRead.readLine();

            if (linha != null) {
                memoria.setMemoriaPosicao(local, linha);
            } else if (linha == null) { // se a leitura da linha for null, significa que a leitura acabou, dai se fecha o 
                //leitor. e retorna fim de leitura
                buffRead.close();
                return false;
            }
        } catch (IOException ex) {
            return false;
        }
        local++;//incremento da posiçao de memoria de armazenamento
        if (local >= 512) { // posicao de armazenamento maior que a capacidade da memoria
            System.out.println("Memoria cheia!!!!");
        }
        //Quando sai do metodo, o buffer continua aberto de modo que quando o metodo eh chamado de novo, continua da linha em
        //que parou. 
        return true;

    }

}
