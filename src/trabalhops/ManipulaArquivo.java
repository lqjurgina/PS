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
import javax.swing.JOptionPane;

/**
 *
 * @author darle
 */
public class ManipulaArquivo {

    public ManipulaArquivo() {
    }

        
    public static void leitor(String path, Memoria memoria) throws IOException {
            BufferedReader buffRead = new BufferedReader(new FileReader(path));
            String linha;
            int local = memoria.getINICIO_INS_DADOS();
            while (true) {
                    linha = buffRead.readLine();
                    if (linha != null) {
                        memoria.setMemoriaPosicao(local, linha);

                    } else
                            break;
                local++;
                if(local == 512){
                    System.out.println("Memoria cheia!!!!");
                }
            }
            buffRead.close();
    }
    
}
