/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.Objects;

/**
 *
 * @author João Leite Nº 8170556
 */
public class Inimigo {
    
    
    private String nome;
    private double poder;
    private String divisao;

    public Inimigo(String nome, double poder, String divisao) {
        this.nome = nome;
        this.poder = poder;
        this.divisao = divisao;
    }

    public Inimigo(String div) {
        this.divisao = div;
    }
    

    public String getNome() {
        return nome;
    }

    public double getPoder() {
        return poder;
    }

    public String getDivisao() {
        return divisao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public String toString() {
        return "Inimigo{" + "nome=" + nome + ", poder=" + poder + ", divisao=" + divisao + '}';
    }
    
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inimigo other = (Inimigo) obj;
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
