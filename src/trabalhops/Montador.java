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
 * @author darle
 */
public class Montador {
    BufferedReader buffRead; //reader do arquivo
    final String OUTPUT_FILE_1 = "tabelaSimbolos.txt";

    public Montador(String path) throws FileNotFoundException {
        try {
            this.buffRead = new BufferedReader(new FileReader(path));//leitor do arquivo
        } catch (IOException ex) {
            Logger.getLogger(ProcessadorMacros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void primeiraPassagem(){
        
    }
}
