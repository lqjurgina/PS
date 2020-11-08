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
public class Registradores {
    
    // Testando Commit
    private short PC, SP, ACC, RI, RE;
    private byte MOP;
    
    //Inicializacao da classe registradores
    Registradores() {
        PC = 0;
        SP = 0;
        ACC = 0;
        RI = 0;
        RE = 0;
        MOP = 0;
    }
    // Retorna todos os registradores menos o MOP
    public short[] getRegistradores() {
        short[] regs = new short[5];
        regs[0] = this.PC;
        regs[1] = this.SP;
        regs[2] = this.ACC;
        regs[3] = this.RI;
        regs[4] = this.RE;

        return regs;
    }
    
    // GET e SET do SP
    public void setSP(short a) {
        this.SP = a;
      
    }
    public short getSP() {
        return this.SP;
    }
    
    // GET e SET do ACC
    public void setACC(short a) {
        this.ACC = a;
     
    }
    public short getACC() {
        return this.ACC;
    }
    
    // GET e SET do registrador RI
    public void setRI(short a) {
        this.RI = a;
        
    }
    public short getRI() {
        return this.RI;
    }
    
    // GET E SET do registrador RE
    public void setRE(short a) {
        this.RE = a;
        
    }
    public short getRE() {
        return this.RE;
    }

    //	GET e SET e INC do registrador PC
    public void setPC(short a) {
        this.PC = a;
     
    }
    public short getPC() {
        return this.PC;
    }
}
