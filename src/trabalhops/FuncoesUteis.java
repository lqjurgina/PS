/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhops;

/**
 *
 * @author Miguel
 */
public class FuncoesUteis {

    /**
     *
     * @param binario String em binário
     * @return Inteiro equivalente
     */
    public static int binaryStringToInt (String binario) {
        if (binario.charAt(0) == '1') // numero negativo -> aplicar complemento de 2
            // inverte bits -> soma 1 -> poe sinal negativo
            return -(Integer.parseInt(inverteBits(binario),2) + 1);
        else 
            return Integer.parseInt(binario,2);
    }
    
    /**
     *
     * @param numero Numero inteiro
     * @param tamanho Tamanho da string em binário desejado
     * @return String com o inteiro convertido em binário + os 0s a esquerda para chegar no tamanho desejado
     * OBS: Caso (numero >= 2^(tamanho)) a string de saída será truncada para os [tamanho] bits menos significativos.
     */
    public static String intToBinaryString (int numero, int tamanho) {
        String saida = Integer.toBinaryString(numero); // essa função já trata o complemento de dois
        tamanho -= saida.length();
        if (tamanho > 0) {
            // preenche com 0s à esquerda
            char [] zeros = new char[tamanho];
            for (int i = 0; i < tamanho; i++) {
                zeros[i] = '0';
            }
            return (new String(zeros)).concat(saida);
        }
        else if (tamanho == 0)
            return saida;
        else  // Se a string binária for maior que o tamanho especificado
            return saida.substring(0-tamanho);
    }
    
    public static String registradorDisplay (Registrador reg){
        Integer valor = FuncoesUteis.binaryStringToInt(reg.getRegistrador());
        return valor.toString();
    
    }
    
    // Inverte os bits de uma string em binário
    private static String inverteBits(String binario) {
        char[] inverso = new char[binario.length()];
            
            for (int i = 0; i < inverso.length; i++)
                inverso[i] = (binario.charAt(i) == '1')?'0':'1'; // se for 0, vira 1, e vice-versa
            
        return new String(inverso);
    }
}
