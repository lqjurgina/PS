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
        //Memory.memoria.addBloco(Integer.toString(this.getSP()), 996);
    }
    public short getSP() {
        return this.SP;
    }
    
    // GET e SET do ACC
    public void setACC(short a) {
        this.ACC = a;
        //Memory.memoria.addBloco(Integer.toString(this.getACC()), 997);
    }
    public short getACC() {
        return this.ACC;
    }
    
    // GET e SET do registrador RI
    public void setRI(short a) {
        this.RI = a;
        //Memory.memoria.addBloco(Integer.toString(this.getRI()), 998);
    }
    public short getRI() {
        return this.RI;
    }
    
    // GET E SET do registrador RE
    public void setRE(short a) {
        this.RE = a;
        //Memory.memoria.addBloco(Integer.toString(this.getRE()), 999);
    }
    public short getRE() {
        return this.RE;
    }

    //	GET e SET e INC do registrador PC
    public void setPC(short a) {
        this.PC = a;
        //Memory.memoria.addBloco(Integer.toString(this.getPC()), 995);
    }
    public short getPC() {
        return this.PC;
    }
    
    // Metodo que testa qual instrucao e incremneta o PC
    public void incPC(int operacao) {// Essa parte tem que mudar para binario
        if (operacao == (2) || operacao == (0) || operacao == (5)
                || operacao == (1) || operacao == (4) || operacao == (15)
                || operacao == (10) || operacao == (3) || operacao == (14)
                || operacao == (12) || operacao == (7) || operacao == (6)
                || operacao == (8)) {
            this.PC += 2;
        } else if (operacao == (16) || operacao == (11)) {
            this.PC++;
        } else if (operacao == (13)) {
            this.PC += 3;
        }
    }
}
