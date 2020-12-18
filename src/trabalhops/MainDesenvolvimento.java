/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lele
 */
public class MainDesenvolvimento {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
      //processadorDeMacros();
      //montador();
      //ligador();
       
    }
    
    public static void processadorDeMacros() throws FileNotFoundException{
         //Desenvolvimento do Processador de Macro
       // ProcessadorMacros PM = new ProcessadorMacros("src/inputs/exemplo_trabalho_com_chamada_de_macros_aninhadas.txt");
       // ProcessadorMacros PM = new ProcessadorMacros("src/inputs/exemplo_trabalho_sem_macros_aninhadas_entrada_processador_de_macros.txt");
       ProcessadorMacros PM = new ProcessadorMacros("src/arquivos/cod2.txt","src/arquivos/cod2.asm.txt");
       //       ProcessadorMacros PM = new ProcessadorMacros("src/arquivos/cod1.txt");
        try {
            PM.leitor();
        } catch (IOException ex) {
            Logger.getLogger(MainDesenvolvimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Done");
    }

    public static void montador() throws FileNotFoundException{
        try {
            Montador M = new Montador("src/arquivos/cod1.asm.txt","src/arquivos/cod2.asm.txt");
            
            M.primeiraPassagemArq1();
            M.primeiraPassagemArq2();
            
            M.segundaPassagemArq1();
            M.segundaPassagemArq2();
        } catch (IOException ex) {
            Logger.getLogger(MainDesenvolvimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void ligador() throws FileNotFoundException{
        try {
            Ligador L = new Ligador("src/arquivos/cod1.obj.txt","src/arquivos/cod2.obj.txt");
            L.imprimeObjs();
        } catch (IOException ex) {
            Logger.getLogger(MainDesenvolvimento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
