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
    
    public Memoria() {
        this.memoria = new ArrayList();
        this.ponteiroPilha = 3;
        memoria.add("0");
        memoria.add("10");
        for (int i = 2; i < TAMANHO_PILHA+2; i++) {
            memoria.add("Pilha");
        }
        for (int i = TAMANHO_PILHA+2; i < TAMANHO_MEMORIA; i++) {
           memoria.add("-");
        }  
        
//        for (int i = 0; i < TAMANHO_MEMORIA; i++) {
//            System.out.println(this.memoria[i]);
//        }
        for (String string : memoria) {
            System.out.println(string);
        }
    }
    public ArrayList<String> getMemoria() {
        return memoria;
    }
    public void imprimeMemoria(){
        for (String string : memoria) {
            System.out.println(string);
        }
    }

    public int getINICIO_INS_DADOS() {
        return this.INICIO_INS_DADOS;
    }
    
    public void setMemoria(int local, String valor){
        this.memoria.set(local, valor);
    }
    
    
    
}
