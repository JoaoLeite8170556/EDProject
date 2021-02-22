/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.*;
import Excepcoes.EmptyExcpetion;
import java.io.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import org.json.simple.parser.ParseException;

/**
 *
 * @author João Leite Nº8170556
 * @author Celio Macedo Nº 8170358
 */
public class Jogo {

    private Mapa mapa;
    private double pontosVida;
    private boolean alvo;
    private String powerUpEscudo;
    private String powerUpVida;

    public Jogo(Mapa mapa) {
        this.pontosVida = 100;
        this.alvo = false;
        this.mapa = mapa;
        this.powerUpEscudo = null;
        this.powerUpVida = null;
    }

    public void modoManual() throws IOException, EmptyExcpetion {

        this.powerUpEscudo = this.mapa.randomRoom();
        this.powerUpVida = this.mapa.randomRoom();

        System.out.println("Codigo da missão: " + this.mapa.getCodMissao() + "\n");
        System.out.println("Versão do mapa: " + this.mapa.getVersao() + "\n");
        System.out.println("Pontos de vida inicias: " + this.pontosVida + "\n");

        String opcao = defineEntrada();
        String opcaotmp = null;
        UnorderedArrayList<String> caminhoPercorrido = new UnorderedArrayList<String>();
        Scanner scanner = new Scanner(System.in, "ISO-8859-1");

        if (!opcao.equals("-1")) {
            caminhoPercorrido.addToRear(opcao);
        }

        do {
            if (opcao.equals(this.mapa.getAlvos().getDivisao())) {
                System.out.println("Encontrou o Alvo!!\n");
                this.alvo = true;
            }

            powerUPs(opcao);

            verificaSeTemInimigos(opcao);

            System.out.println("Divisao atual: " + opcao + "\n");
            mapa.getDivisoes().mostraVerticesAdjacentes(opcao);
            System.out.println("Sair\n");

            while (opcaotmp != opcao) {
                System.out.println("Escolha divisao para onde se quer mover: ");
                opcaotmp = scanner.nextLine();

                if (opcaotmp.equals("Sair")) {
                    System.out.println("Saiu do Jogo!!\n");
                    opcao = opcaotmp;
                } else {
                    if (!verificaOpcao(opcao, opcaotmp).equals("invalido")) {
                        opcao = opcaotmp;

                    } else {
                        System.out.println("\nDivisao invalida, por favor tente outra vez!!\n");
                    }
                }

            }
            opcaotmp = null;
            if (opcao.equals("Sair")) {
                this.pontosVida = 0;
            } else {
                caminhoPercorrido.addToRear(opcao);
            }

        } while (this.pontosVida > 0 && verificarSaidas(opcao) == null);

        verificaSeGanhou();

    }

    private void powerUPs(String divisao) {
        if (this.powerUpEscudo.equals(divisao) && this.powerUpVida.equals(divisao)) {
            this.powerUpEscudo = "Equipado";
            this.powerUpVida = "Utilizada";
            this.pontosVida = this.pontosVida + 40;
            System.out.println("\nEscudo equipado!!! ARRRRRRRRRRRR\n");
            System.out.println("PowerUp Vida ativado" + this.pontosVida + "\n");
        } else if (this.powerUpEscudo.equals(divisao)) {
            this.powerUpEscudo = "Equipado";
            System.out.println("\nEscudo equipado!!! ARRRRRRRRRRRR\n");
        } else if (this.powerUpVida.equals(divisao)) {
            this.powerUpVida = "Utilizada";
            this.pontosVida = 100;
            System.out.println("\nPowerUp Vida ativado" + this.pontosVida + "\n");
        }
    }

    /**
     * Este método vai avaliar se o To Cruz completou a sua missão com sucesso
     */
    private void verificaSeGanhou() {

        if (this.pontosVida > 0 && this.alvo == true) {
            System.out.println("\nParabens ganhou!!");
        } else if (this.pontosVida < 0) {
            System.out.println("\nVoce perdeu, ficou sem vida!!\nTente outra vez mais tarde, "
                    + "Boa Sorte para a próxima!!\n");
        } else if (this.alvo == false) {
            System.out.println("\nConsguiu sair mas acho que se esqueceu de algo!!"
                    + "\n" + "Boa Sorte para a próxima!!");
        }
    }

    /**
     * Verifica se na divisao recebida tem inimigos e caso tenha é retirado o
     * dano
     *
     * @param divisao divisao que se pretende verificar
     * @param pontosDeVida Pontos de vida do utilizador
     * @return Pontos de vida que vai ter quando sair da divisao
     */
    private double verificaSeTemInimigos(String divisao) {

        Iterator itr = this.mapa.getInimigos().iterator();
        while (itr.hasNext()) {
            Inimigo inimigo = (Inimigo) itr.next();
            if (inimigo.getDivisao().equals(divisao)) {
                if (this.powerUpEscudo.equals("Equipado")) {

                    this.powerUpEscudo = "Destruido";
                    System.out.println("Foi por pouco, cuidado para a proxima"
                            + " o seu escudo foi destruido!!");

                } else {

                    this.pontosVida = this.pontosVida - inimigo.getPoder();
                    System.out.println("Levou " + inimigo.getPoder() + " de dano, tem mais cuidado!!");
                }

            }
        }
        System.out.println("\n A sua vida atual é: " + this.pontosVida + "\n");
        return this.pontosVida;
    }

    /**
     * Verifica se a divisao escolhida pelo utilizador para a proxima jogada é
     * valida
     *
     * @param opcao divisao em que se encontra o TO Cruz
     * @param opcaoTmp divisao para a qual o To Cruz se quer mover
     * @return "invalido" caso a divisao escolhida nao seja valida e caso seja
     * valida é retornado a opcao para onde o To Cruz se vai mover
     */
    public String verificaOpcao(String opcao, String opcaoTmp) {

        Iterator itr = mapa.getDivisoes().obtemVerticesAdjacentes(opcao);

        while (itr.hasNext()) {
            if (itr.next().equals(opcaoTmp)) {
                return opcaoTmp;
            }
        }
        return "invalido";
    }

    /**
     * Este metodo vai verificar se o target existe como entrada no JSON
     *
     * @param target target a procurar
     * @return o target se existir, se não existir, retorna null;
     * @throws IOException a
     * @throws ArrayIsEmpty
     */
    private String verificarEntrada(String target) throws IOException, EmptyExcpetion {

        UnorderedArrayList<String> tempList = mapa.getEntradas();

        boolean tempBool = tempList.contains(target);

        if (tempBool == true) {
            return target;
        } else {
            return null;
        }
    }

    /**
     * Este método vai imprimir todas as entradas no json.
     *
     * @param mapa mapa onde queres imprimir as entradas.
     */
    public void imprimeEntradas() {
        Iterator iterator = this.mapa.getEntradas().iterator();

        System.out.println("Entradas possiveis:" + "\n");
        while (iterator.hasNext()) {
            System.out.println("-> " + iterator.next());

        }

        System.out.println("\n");

    }

    /**
     * Metodo que vai pedir qual a entrada que o deseja que o Tó Cruz começe.
     *
     * @return a entrada
     * @throws IOException
     */
    public String defineEntrada() throws IOException, EmptyExcpetion {

        imprimeEntradas();
        Scanner scanner = new Scanner(System.in);
        String temp = "";

        do {
            System.out.println("Introduza a entrada na qual o Tó Cruz "
                    + "deve começar a missão: " + "\n");
            System.out.println("Para terminar selecione: -1" + "\n");

            temp = scanner.nextLine();

            if (temp.equals("-1")) {
                System.out.println("Saiu do programa");
            }

            if (temp.equals(verificarEntrada(temp))) {
                System.out.println("A entrada escolhida é " + temp + "\n");
            }

        } while (!temp.equals(verificarEntrada(temp)) && !temp.equals("-1"));
        return temp;

    }

    /**
     * Este metodo vai verificar se o target existe como saida no JSON
     *
     * @param target a saida a verificar se existe
     * @return o target se existir, se não retorna null
     * @throws IOException
     * @throws ArrayIsEmpty
     */
    private String verificarSaidas(String divisao) throws IOException, EmptyExcpetion {

        UnorderedArrayList<String> tempList = mapa.getSaidas();

        boolean tempBool = tempList.contains(divisao);
        
        Scanner scanner = new Scanner(System.in, "ISO-8859-1");
        
        int opcaotmp;
        
        if (tempBool == true) {
            if(this.alvo!=true){
                System.out.println("Ainda nao tem o alvom tem a certeza que quer sait?!\n");
                System.out.println("1- Sim;\n");
                System.out.println("2- Não;\n");
                do{
                opcaotmp = scanner.nextInt();
                
                switch (opcaotmp) {
                    case 1:
                        System.out.println("\nA sair....\n");
                        return divisao;          
                    case 2:
                        System.out.println("Boa Sorte para o resto do jogo!!\n");
                        return null;
                }
                
                if(opcaotmp!=1 && opcaotmp!=2){
                    System.out.println("Valor invalido, escolha outra opção \n");
                }
                }while(opcaotmp!=0);
            }else{
                return divisao;
            }
            
        } 
            return null;
        
    }

    public double getPontosVida() {
        return pontosVida;
    }

}
