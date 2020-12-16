/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author darle
 */
public class Ligador {
    BufferedReader buffRead1,buffRead2;
    
    public Ligador(String path1, String path2) throws FileNotFoundException {
        this.buffRead1 = new BufferedReader(new FileReader(path1));//leitor do arquivo
        this.buffRead2 = new BufferedReader(new FileReader(path2));
    }
    
    public void imprimeObjs() throws IOException{
        String linha1 = buffRead1.readLine();
        
        System.out.println("OBJ1");
        while (linha1 != null ){
            System.out.println(linha1);
            linha1 = buffRead1.readLine();
        }
        System.out.println("==================");
        String linha2 = buffRead2.readLine();
        System.out.println("OBJ2");
        while (linha2 != null ){
            System.out.println(linha2);
            linha2 = buffRead2.readLine();
        }
    }
}
