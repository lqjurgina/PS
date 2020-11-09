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
        return Integer.parseInt(binario,2);
    }
    
    /**
     *
     * @param numero Numero inteiro
     * @param tamanho Tamanho da string em binário desejado
     * @return String com o inteiro convertido em binário + os 0s a esquerda para chegar no tamanho desejado
     * OBS: Caso (numero >= 2^(tamanho)) a string de saída será maior que tamanho.
     */
    public static String intToBinaryString (int numero, int tamanho) {
        String saida = Integer.toBinaryString(numero);
        tamanho -= saida.length();
        if (tamanho > 0) {
            // preenche com 0s à esquerda
            char [] zeros = new char[tamanho];
            for (int i = 0; i < tamanho; i++) {
                zeros[i] = '0';
            }
            return (new String(zeros)).concat(saida);
        }
        else 
            return saida;
    }
}
