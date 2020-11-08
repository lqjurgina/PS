/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

/**
 *
 * @author darle
 */
public class Registrador{
    
    int posicao;//posicao da memoria que esse registrador ocupa
    Memoria m;//memoria a qual o registrador esta atrelado 
    
//Inicializacao da classe registradores
    Registrador(Memoria m, int posicao) {
        this.m = m;
        this.posicao = posicao;
    }
   
    //getters e setters do valor do registrador
    public void setRegistrador(String a) {
        m.setMemoriaPosicao(posicao,a);
     
    }
    public String getRegistrador() {
        return m.getMemoriaPosicao(posicao);
    }
    
}
