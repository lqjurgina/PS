/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.util.ArrayList;

/**
 *
 * @author darle
 */
public class Memoria {

    private ArrayList<String> memoria;
    private final int TAMANHO_MEMORIA = 512;
    private int ponteiroPilha;
    private final int TAMANHO_PILHA = 10;
    private final int INICIO_INS_DADOS = 12;
    private final int QUANT_REGISTRADORES = 6;
    
    public Memoria(Registrador[] regs) {//A memoria recebe como parametro os registradores que vao ser inseridos nela
        this.memoria = new ArrayList();//cria a memoria
        this.ponteiroPilha = 3;

        memoria.add("0");//primeira posição do vetor memória começa com o valor 0
        memoria.add("10");//segunda posição do vetor memória começa com o valor 10(tamanho da pilha)
        for (int i = 2; i < TAMANHO_PILHA + 2; i++) {//aloca a Pilha
            memoria.add("Pilha");
        }
        for (int i = TAMANHO_PILHA + 2; i < TAMANHO_MEMORIA - QUANT_REGISTRADORES; i++) {//Aloca memórias de dados e instruções
            memoria.add("-");
        }
        for (int i = TAMANHO_MEMORIA - QUANT_REGISTRADORES,c=0; i < TAMANHO_MEMORIA; i++,c++) {//aloca posicção dos registradores
            regs[c] = new Registrador(this,i);//cria um registrador e o atrela a essa memoria
        }

        memoria.add("0000000000001100");//PC
        memoria.add("0000000000000010"); //SP
        memoria.add("0000000000000000"); //ACC
        memoria.add("xx"); //OPM
        memoria.add("000"); //IR
        memoria.add("0000000000001100");//IM

        //Seta os valores dos registradores
//        regs[0].add("0000000000001100");//PC
//        regs[1].add("0000000000000010");//SP
//        regs[2].add("0000000000000000");//ACC
//        regs[3].add("xx");//OPM
//        regs[4].add("000");//IR
//        regs[5].add("0000000000001100");//IM
//        

        /*for (int i = 0; i < TAMANHO_MEMORIA; i++) {
            System.out.println(this.memoria[i]);
        }
        for (String string : memoria) {
            System.out.println(string);
        }*/
    }

    //retorna a string memória. Tomar cuida ao manipular a memória com esse método.
    public ArrayList<String> getMemoria() {
        return memoria;
    }

    //imprime memória aqui
    public void imprimeMemoria() {
        for (String string : memoria) {
            System.out.println(string);
        }
        //Falta fazer função que imprime na memória da interface.  
    }

    public void imprimeMemoriaParcial(int inicio, int fim) {//funcao para observar regioes especificas da memoria
        for (int c = inicio; c < fim; c++) {
            System.out.println(memoria.get(c));
        }
    }

    //Pode ser usado para setar o primeiro valor de PC
    public int getINICIO_INS_DADOS() {
        return this.INICIO_INS_DADOS;
    }

    //escolhe uma posição da memória(int) e atualiza ela com uma novo valor(string)
    public void setMemoriaPosicao(int local, String valor) {
        this.memoria.set(local, valor);
    }

    //Retorna uma posição da memória
    public String getMemoriaPosicao(int local) {
        return this.memoria.get(local);
    }
    
    // Retorna uma posição da memória, convertido para inteiro
    public int getMemoriaPosicaoInt(int local) {
        return FuncoesUteis.binaryStringToInt(this.getMemoriaPosicao(local));
    }

    public int getTAMANHO_MEMORIA() {
        return TAMANHO_MEMORIA;
    }

}
