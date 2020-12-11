/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author darle
 */
public class MainMontadorTeste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Montador M = new Montador("src/inputs/cod.asm.txt");
        M.primeiraPassagem();
    }
    
}
