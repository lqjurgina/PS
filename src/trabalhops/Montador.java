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
import java.util.Arrays;
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
    BufferedWriter buffWriterObj1,buffWriterObj2,buffWriterLst1,buffWriterLst2;
    String saidaObj1 = "src/arquivos/cod1.obj.txt";
    String saidaObj2 = "src/arquivos/cod2.obj.txt";
    
    String saidaLst1 = "src/arquivos/cod1.lst.txt";
    String saidaLst2 = "src/arquivos/cod2.lst.txt";
    
    final String OUTPUT_FILE_1 = "tabelaSimbolos1.txt";
    private Map<String, Integer> tabelaSimbolos1,tabelaSimbolos2,tabelaDefEXTDEF1,tabelaDefEXTDEF2;
    
    private ArrayList<String> tabela1,tabela2;
    
    private int PosMem1 = 0;//posição memória código1
    private int PosMem2 = 0;//posição memória código2
    private int TamLinhasMem1 = 0;//Indica a quantidade de linhas que o OBJ 1 vai ter
    private int TamLinhasMem2 = 0;//Indica a quantidade de linhas que o OBJ 1 vai ter
    
    //Auxiliares para manter os códigos obj
    ArrayList<String> obj1  = new ArrayList();
    ArrayList<String> obj2  = new ArrayList();
    
    //Auxiliares para manter os códigos 
    ArrayList<String> lst1  = new ArrayList();
    ArrayList<String> lst2  = new ArrayList();
    
    public Montador(String path1, String path2) throws FileNotFoundException {
        //auxiliares para primeira passagem
        this.tabelaSimbolos1 = new HashMap<String, Integer>();
        this.tabelaSimbolos2 = new HashMap<String, Integer>();
        
        //auxiliares para primeira 
        this.tabelaDefEXTDEF1 = new HashMap<String, Integer>();
        this.tabelaDefEXTDEF2 = new HashMap<String, Integer>();
        
        //Auxiliares para criar a TABELA dos arquivos .obj
        this.tabela1 = new ArrayList();
        this.tabela2 = new ArrayList();
        try {
            //para Leitura da primeira passagem
            this.buffRead1 = new BufferedReader(new FileReader(path1));//leitor do arquivo
            this.buffRead12 = new BufferedReader(new FileReader(path2));//leitor do arquivo
            
            //Para Leitura da segunada passagem
            this.buffRead2 = new BufferedReader(new FileReader(path1));//leitor do arquivo
            this.buffRead22 = new BufferedReader(new FileReader(path2));//leitor do arquivo
        
            //Para criação dos arquivos objetos
            this.buffWriterObj1 = new BufferedWriter(new FileWriter(saidaObj1));
            this.buffWriterObj2 = new BufferedWriter(new FileWriter(saidaObj2));
            
            //Para criação dos arquivos LST            
            this.buffWriterLst1 = new BufferedWriter(new FileWriter(saidaLst1));
            this.buffWriterLst2 = new BufferedWriter(new FileWriter(saidaLst2));
        
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
        
        buffRead1.close();
      
    }
    public void primeiraPassagemArq2() throws IOException{
        primeiraPassagem(buffRead12,this.tabelaSimbolos2,this.PosMem2,this.tabelaDefEXTDEF2);
        
        System.out.println("======== asm 2 ========");
        System.out.println("Tabela de simbolos");
        System.out.println(tabelaSimbolos2.toString());
        System.out.println("Tabela de definições EXTDEF");
        System.out.println(tabelaDefEXTDEF2.toString());
        
        buffRead12.close();
    }
    
    public void segundaPassagemArq1() throws IOException{
        segundaPassagem(
                this.buffRead2,
                this.lst1,
                this.obj1,
                this.tabelaSimbolos1,
                this.tabelaDefEXTDEF1,
                this.TamLinhasMem1,
                this.tabela1
        );
        //System.out.println("OBJ1 = "+ obj1.toString());
        
        System.out.println("* cod1.obj\n");
        buffWriterObj1.append("* cod1.obj\n");
        for (String string : this.obj1) {
            System.out.println(string);
            buffWriterObj1.append(string+"\n");
        }
        System.out.println("TABELA");
        buffWriterObj1.append("TABELA\n");
        
        for (String string : this.tabela1) {
            System.out.println(string);
            buffWriterObj1.append(string+"\n");
        }
        buffWriterObj1.append("TAMANHO\n" + obj1.size());
        System.out.println("TAMANHO\n" + obj1.size());
        
        System.out.println("=================");
        buffWriterLst1.append("LINHA CÓDIGO INSTRUÇÃO\n");
        for (int i = 0; i < this.lst1.size(); i++) {
            System.out.println(i + " " + obj1.get(i) + " " + lst1.get(i));
            buffWriterLst1.append(i + " " + obj1.get(i) + " " + lst1.get(i)+ "\n");
        }
        
        //Escreve nos arquivos LST e OBJ
        buffWriterLst1.flush();
        buffWriterObj1.flush();
        
        //Fecha todos os buffers abertos
        buffWriterLst1.close();
        buffWriterObj1.close();        
        buffRead2.close();
    }
    public void segundaPassagemArq2() throws IOException{
        segundaPassagem(
                this.buffRead22,
                this.lst2,
                this.obj2, 
                this.tabelaSimbolos2, 
                this.tabelaDefEXTDEF2, 
                this.TamLinhasMem2, 
                this.tabela2
        );
        
        System.out.println("* cod2.obj\n");
        buffWriterObj2.append("* cod2.obj\n");
        
        for (String string : this.obj2) {
           System.out.println(string);
           buffWriterObj2.append(string+"\n");
        }
        System.out.println("TABELA");
        buffWriterObj2.append("TABELA\n");
        
        for (String string : this.tabela2) {
            System.out.println(string);
            buffWriterObj2.append(string+"\n");
        }
        System.out.println("TAMANHO\n" + obj2.size());
        buffWriterObj2.append("TAMANHO\n" + obj2.size());

        System.out.println("=================");
        buffWriterLst2.append("LINHA CÓDIGO INSTRUÇÃO\n");
        for (int i = 0; i < this.lst2.size(); i++) {
            System.out.println(i + " " + obj2.get(i) + " " + lst2.get(i));
            buffWriterLst2.append(i + " " + obj2.get(i) + " " + lst2.get(i) + "\n");
        }
        
        //Escreve nos arquivos LST e OBJ
        buffWriterLst2.flush();
        buffWriterObj2.flush();
        
        //Fecha todos os buffers abertos
        buffWriterLst2.close();    
        buffWriterObj2.close();         
        buffRead22.close();
        
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
                    if (d[i].startsWith("*")){
                        i = d.length;
                        break;
                    }
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
    
   public void segundaPassagem(
            BufferedReader buffRead, 
            ArrayList<String> lst,
            ArrayList<String> obj, 
            Map<String, Integer> tabelaSimbolos,
            Map<String, Integer> tabelaDefEXTDEF,
            int TamLinhasMem,
            ArrayList<String> auxTabela
    ) throws IOException    {
        String[] d;
        
        String teste;
        String linha = buffRead.readLine();
        

        while (linha != null) {
                           
            d = linha.split(" ");
           
            for (int i = 0; i < d.length; i++) {
                //System.out.println(d[i]);
                switch (d[i]) {

                    case "ADD":
                        lst.add("ADD");
                        obj.add(FuncoesUteis.intToBinaryString(2,16));
                        teste = d[i+1];
                        
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "BR":
                        lst.add("BR");
                        obj.add(FuncoesUteis.intToBinaryString(0,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "BRNEG":
                        lst.add("BRNEG");
                        obj.add(FuncoesUteis.intToBinaryString(5,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    
                    break;

                    case "BRPOS":
                        lst.add("BRPOS");
                        obj.add(FuncoesUteis.intToBinaryString(1,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                          
                    
                    break;
                    case "BRZERO":
                        lst.add("BRZERO");
                        obj.add(FuncoesUteis.intToBinaryString(4,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst,obj);
                        
                        TamLinhasMem++;
                    
                    break;
                    case "CALL":
                        lst.add("CALL");
                        obj.add(FuncoesUteis.intToBinaryString(15,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    
                    break;
                    case "COPY"://Voltar aqui, muito complexo
                        lst.add("COPY");
                        obj.add(FuncoesUteis.intToBinaryString(13,16));
                        
                        //Primeiro Operando
                        teste = d[i+1];
                        char[] auxVetString;
                        auxVetString = obj.get(TamLinhasMem).toCharArray();

                        if(teste.startsWith("#")){// Operando imediato no início
                           //Operando 1 não pode ser imediato
                            System.err.println("Operando 1 não pode ser imediato");

                        }else if(teste.endsWith(",I")){//Operando indireto
                            lst.add(teste.substring (0, teste.length()-2));
                            
                            auxVetString[10] = '1';
                            obj.set(TamLinhasMem, String.valueOf(auxVetString));
                            obj.add(teste.substring (0, teste.length()-2));

                        }else{//Operando direto
                            lst.add(teste);
                            obj.add(teste);
                        }
                        //TamLinhasMem++;
                        
                        //Segundo Operando
                        teste = d[i+2];
                        auxVetString = obj.get(TamLinhasMem).toCharArray();
                        
                        if(teste.startsWith("#")){// Segundo Operando imediato no início
                            String aux = teste.substring (1, teste.length());
                            lst.add(aux);
                            auxVetString[8] = '1';
                            obj.set(TamLinhasMem, String.valueOf(auxVetString));
                            

                            obj.add(FuncoesUteis.intToBinaryString(Integer.parseInt(aux), 16));

                        }else if(teste.endsWith(",I")){//Segundo Operando indireto
                            lst.add(teste.substring (0, teste.length()-2));
                            
                            auxVetString[9] = '1';
                            obj.set(TamLinhasMem, String.valueOf(auxVetString));
                            obj.add(teste.substring (0, teste.length()-2));

                        }else{// Segundo operando direto
                            obj.add(teste);
                            lst.add(teste);
                        }
                        TamLinhasMem++;
                    
                    break;
                    case "DIVIDE":
                        lst.add("DIVIDE");
                        obj.add(FuncoesUteis.intToBinaryString(10,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "LOAD":
                        lst.add("LOAD");
                        obj.add(FuncoesUteis.intToBinaryString(3,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "MULT":
                        lst.add("MULT");
                        obj.add(FuncoesUteis.intToBinaryString(14,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    
                    break;
                    case "READ":
                        lst.add("READ");
                        obj.add(FuncoesUteis.intToBinaryString(12,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "RET":
                        lst.add("RET");
                        obj.add(FuncoesUteis.intToBinaryString(9,16));
                    break;
                    case "STOP":
                        lst.add("STOP");
                        obj.add(FuncoesUteis.intToBinaryString(11,16));
                    break;
                    case "STORE":
                        lst.add("STORE");
                        obj.add(FuncoesUteis.intToBinaryString(7,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "SUB":
                        lst.add("SUB");
                        obj.add(FuncoesUteis.intToBinaryString(6,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "WRITE":
                        lst.add("WRITE");
                        obj.add(FuncoesUteis.intToBinaryString(8,16));
                        teste = d[i+1];
                        
                        testaOperandoSegundaPassagem(teste,TamLinhasMem, lst, obj);
                        
                        TamLinhasMem++;
                    break;
                    case "END":
                    break;
                    case "SPACE":
                        lst.add("SPACE");
                        obj.add(FuncoesUteis.intToBinaryString(0,16));
                        //obj.add(teste)
                    break;
                    case "CONST":
                        teste = d[i+1];
                        lst.add(teste);
                        obj.add(FuncoesUteis.intToBinaryString(Integer.parseInt(teste),16));
                    break;
                    default:
                        //Rótulo Labol
                        if(i == 0){                                
                            if(tabelaDefEXTDEF.containsKey( d[i] )){
                                
                                auxTabela.add(d[i] +" " + obj.size());
                                //obj1.add(auxTabela);
                                //System.out.println(auxTabela);
                            }
                            if(tabelaSimbolos.containsKey( d[i] )){
                                
                                if(tabelaSimbolos.get(d[i]) != null){
                                    auxTabela.add(d[i] + " " + obj.size());
                                    //obj1.add(auxTabela);
                                    //System.out.println(auxTabela);
                                }
                                
                            }
                        }   
                    break;
                        
                }
            }
            linha = buffRead.readLine(); // lê da segunda até a última linha
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
    
        public void testaOperandoSegundaPassagem(String teste,int TamLinhasMem, ArrayList<String> lst, ArrayList<String> obj){
        char[] auxVetString;
        auxVetString = obj.get(TamLinhasMem).toCharArray();
        
        if(teste.startsWith("#")){// Operando imediato no início
            
            auxVetString[8] = '1';
            obj.set(TamLinhasMem, String.valueOf(auxVetString));
            String aux = teste.substring (1, teste.length());
            
            lst.add(aux);
            obj.add(FuncoesUteis.intToBinaryString(Integer.parseInt(aux), 16));

        }else if(teste.endsWith(",I")){//Operando indireto

            auxVetString[10] = '1';
            obj.set(TamLinhasMem, String.valueOf(auxVetString));
            
            lst.add(teste.substring (0, teste.length()-2));
            obj.add(teste.substring (0, teste.length()-2));

        }else{
            lst.add(teste);
            obj.add(teste);
        }
    }
    
}
