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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author darle
 */
public class Montador {
    BufferedReader buffRead1,buffRead2; //reader do arquivo
    final String OUTPUT_FILE_1 = "tabelaSimbolos.txt";
    private Map<String, Integer> tabelaSimbolos;
    private int PosMem = 0;//primeira posição de memória da máquina T1
    
    public Montador(String path) throws FileNotFoundException {
        this.tabelaSimbolos = new HashMap<String, Integer>();
        
        try {
            this.buffRead1 = new BufferedReader(new FileReader(path));//leitor do arquivo
            this.buffRead2 = new BufferedReader(new FileReader(path));//leitor do arquivo
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessadorMacros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void primeiraPassagem() throws IOException{
        String[] d;
        
        boolean isLeftLabal = false;
        String linha = buffRead1.readLine();
        
        while (linha != null ) {
                 
                d = linha.split(" ");

                for (int i = 0; i < d.length; i++) {
                    //System.out.println(d[i]);
                    switch (d[i]) {
                        
                        case "ADD":

                            String aux;
                            aux = d[i+1];
                            testaOperando(aux);

                        break;
                        case "BR":
                            
                        break;
                        case "BRNEG":

                        break;

                        case "BRPOS":
                        break;
                        case "BRZERO":
                        break;
                        case "CALL":
                        break;
                        case "COPY":
                        break;
                        case "DIVIDE":
                        break;
                        case "LOAD":
                        break;
                        case "MULT":
                        break;
                        case "READ":
                        break;
                        case "RET":
                        break;
                        case "STOP":
                        break;
                        case "STORE":
                        break;
                        case "SUB":
                        break;
                        case "WRITE":
                        break;
                        case "END":
                        break;
                        default:
                            //Rótulo Labol
                            if(i == 0){
                                boolean testaSeJaTem = false;//Serve para ver se o Rótulo já está na tabela
                            
                            
                                for (String key : tabelaSimbolos.keySet()) {
                                    if(key == d[i]){
                                        testaSeJaTem = true;
                                    }
                                }
                                if(!testaSeJaTem){
                                    tabelaSimbolos.put(d[i], PosMem);
                                    System.out.println(d[i] + " = " + PosMem);//key + " = " + value);
                                    //PosMem++;
                                }
                                isLeftLabal = true;
                            }   
                        break;
                    }
                    if(!isLeftLabal){
                        PosMem++; 
                    }else{
                        isLeftLabal = false;
                    }
                        
                        
                }
                //System.out.printf("%s\n", linha);

                linha = buffRead1.readLine(); // lê da segunda até a última linha
            }
    }
    
    public void testaOperando(String aux){
        if(aux.startsWith("#")){// Operando imediato no início
                                
        }else if(aux.endsWith(",I")){//Operando indireto
            if (aux.length() > 0) {
            aux = aux.substring (0, aux.length() - 2);

            boolean testaSeJaTem = false;//Serve para ver se o Rótulo já está na tabela                        
            for (String key : this.tabelaSimbolos.keySet()) {
                if(key == aux){
                    testaSeJaTem = true;
                }
            }
            if(!testaSeJaTem){
                this.tabelaSimbolos.put(aux, null);
                System.out.println(aux + " = " + null);
            }
        //indireto();
        }                         
        }else{//Operando Direto,
            boolean testaSeJaTem = false;//Serve para ver se o Rótulo já está na tabela                        
            for (String key : tabelaSimbolos.keySet()) {
                if(key == aux){
                    testaSeJaTem = true;
                }
            }
            if(!testaSeJaTem){
                tabelaSimbolos.put(aux, null);
                System.out.println(aux + " = " + null);
            }
        }
    }
}
