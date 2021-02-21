/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

/**
 *
 * @author João Leite Nº 8170556
 */
public class Alvo {
    
    private String tipo;
    private String divisao;

    public Alvo() {}

    public Alvo(String tipo, String divisao) {
        this.tipo = tipo;
        this.divisao = divisao;
    }
    

    public String getTipo() {
        return tipo;
    }

    public String getDivisao() {
        return divisao;
    }

    @Override
    public String toString() {
        return "Alvo{" + "tipo=" + tipo + ", divisao=" + divisao + '}';
    }
  
}
