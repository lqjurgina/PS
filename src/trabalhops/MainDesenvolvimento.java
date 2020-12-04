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
       ProcessadorMacros PM = new ProcessadorMacros("src/inputs/exemplo_trabalho_sem_macros_aninhadas.txt");
        try {
            PM.leitor();
        } catch (IOException ex) {
            Logger.getLogger(MainDesenvolvimento.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("DOne");
    }
    
}
