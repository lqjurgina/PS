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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author darle
 */
public class Montador {
    BufferedReader buffRead1,buffRead12,buffRead2,buffRead22; //reader do arquivo
    final String OUTPUT_FILE_1 = "tabelaSimbolos1.txt";
    private Map<String, Integer> tabelaSimbolos1,tabelaSimbolos2,tabelaDefEXTDEF1,tabelaDefEXTDEF2;
    private int PosMem1 = 0;//posição memória código1
    private int PosMem2 = 0;//posição memória código2
    
    ArrayList<String> obj1  = new ArrayList();
    ArrayList<String> obj2  = new ArrayList();
    
    public Montador(String path1, String path2) throws FileNotFoundException {
        this.tabelaSimbolos1 = new HashMap<String, Integer>();
        this.tabelaSimbolos2 = new HashMap<String, Integer>();
        
        this.tabelaDefEXTDEF1 = new HashMap<String, Integer>();
        this.tabelaDefEXTDEF2 = new HashMap<String, Integer>();
        
        try {
            //para primeira passagem
            this.buffRead1 = new BufferedReader(new FileReader(path1));//leitor do arquivo
            this.buffRead12 = new BufferedReader(new FileReader(path2));//leitor do arquivo
            
            //Paara segunada passagem
            this.buffRead2 = new BufferedReader(new FileReader(path1));//leitor do arquivo
            
        } catch (IOException ex) {
            Logger.getLogger(ProcessadorMacros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void primeiraPassagemArq1() throws IOException{
        primeiraPassagem(buffRead1,this.tabelaSimbolos1,this.PosMem1,this.tabelaDefEXTDEF1);
        System.out.println("======== asm 1 ========");
        System.out.println("Tabela de simbolos");
        System.out.println(tabelaSimbolos1.toString());
        System.out.println("Tabela de definições EXTDEF");
        System.out.println(tabelaDefEXTDEF1.toString());
    }
    public void primeiraPassagemArq2() throws IOException{
        primeiraPassagem(buffRead12,this.tabelaSimbolos2,this.PosMem2,this.tabelaDefEXTDEF2);
        
        primeiraPassagem(buffRead1,this.tabelaSimbolos1,this.PosMem1,this.tabelaDefEXTDEF1);
        System.out.println("======== asm 2 ========");
        System.out.println("Tabela de simbolos");
        System.out.println(tabelaSimbolos2.toString());
        System.out.println("Tabela de definições EXTDEF");
        System.out.println(tabelaDefEXTDEF2.toString());
    }
    
    
    public void primeiraPassagem(
            BufferedReader buffRead,
            Map<String, Integer> tabelaSimbolos,
            int PosMem,
            Map<String, Integer> tabelaDefEXTDEF
    ) throws IOException{
        String[] d;
        
        boolean isLeftLabal = false;
        String linha = buffRead.readLine();
        
        while (linha != null ) {
                
                d = linha.split(" ");
                
                String aux;
                for (int i = 0; i < d.length; i++) {
                    //System.out.println(d[i]);
                    switch (d[i]) {
                        
                        case "ADD":
                        case "BR":
                        case "BRNEG":
                        case "BRPOS":
                        case "BRZERO":
                        case "CALL":
                        case "DIVIDE":
                        case "LOAD":
                        case "MULT":
                        case "READ":
                        case "STORE":
                        case "SUB":
                        case "WRITE":
                            aux = d[i+1];
                            testaOperando(aux, tabelaSimbolos);
                        break;

                        case "COPY":
                            aux = d[i+1];
                            testaOperando(aux,tabelaSimbolos);
                            aux = d[i+2];
                            testaOperando(aux,tabelaSimbolos);
                        break;
                                             
                        case "RET":
                        break;
                        case "STOP":
                        break;

                        case "END":
                        break;  
                        case "EXTDEF":
                            aux = d[i+1];
                            tabelaDefEXTDEF.put(aux, null);
                        break;  
                        case "EXTR":
                            
                        break;  
                        case "STACK":
                        break;
                        case "CR":
                        break;
                        case "LF":
                        break;
                        case "*":
                            isLeftLabal = true;
                        break;
                        
                        case "START":
                            
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
                                   
                                   // System.out.println(d[i] + " = " + PosMem);//key + " = " + value);
                                    //PosMem++;
                                }
                                isLeftLabal = true;
                            }   
                        break;
                    }
                    if(!isLeftLabal && !d[i].equals("CR") ){
                        PosMem++; 
                    }else{
                        isLeftLabal = false;
                    }
                        
                        
                }
                //System.out.printf("%s\n", linha);

                linha = buffRead.readLine(); // lê da segunda até a última linha
            }

         Iterator it =  tabelaSimbolos.keySet().iterator();

        while(it.hasNext()) {
            Object key=it.next();
            for (Map.Entry<String,Integer> pair1 : tabelaDefEXTDEF.entrySet()) {
                if(key.equals(pair1.getKey())){
                    
                    pair1.setValue(tabelaSimbolos.get(key));
                    it.remove();
                }
            }
        }
       
    }
    
    public void segundaPassagem() throws IOException{
        String[] d;
        String transforma ;
        
        
        boolean isLeftLabal = false;
        String linha = buffRead2.readLine();
        
        while (linha != null ) {
                
            d = linha.split(" ");

            String aux;
            for (int i = 0; i < d.length; i++) {
                //System.out.println(d[i]);
                switch (d[i]) {

                    case "ADD":
                    
                        
//                        obj1.add(FuncoesUteis.intToBinaryString(2,16));
//                        System.out.println(obj1.toString());
                                
                        //obj1.add(d[i+1]);
                    break;
                    case "BR":
//                        obj1.add(d[i]);
//                        obj1.add(d[i+1]);  
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
                        obj1.add(FuncoesUteis.intToBinaryString(3,16));
                        String teste = d[i+1];
                        if(teste.startsWith("#")){// Operando imediato no início
                                
                        }else if(teste.endsWith(",I")){//Operando indireto
                            
                        }else{//Operando Direto
                            
                        }
                        System.out.println(obj1.toString());
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
                }
            }
        }
    }
    
    
    public void testaOperando(String aux, Map<String, Integer> tabelaSimbolos){
        if(aux.startsWith("#")){// Operando imediato no início
                                
        }else if(aux.endsWith(",I")){//Operando indireto
            if (aux.length() > 0) {
            aux = aux.substring (0, aux.length() - 2);

            boolean testaSeJaTem = false;//Serve para ver se o Rótulo já está na tabela                        
            for (String key : tabelaSimbolos.keySet()) {
                if(key == aux){
                    testaSeJaTem = true;
                }
            }
            if(!testaSeJaTem){
                tabelaSimbolos.put(aux, null);
                //System.out.println(aux + " = " + null);
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
                //System.out.println(aux + " = " + null);
            }
        }
    }
}
