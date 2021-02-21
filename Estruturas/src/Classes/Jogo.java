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
import javax.print.DocFlavor;

/**
 *
 * @author João Leite Nº8170556
 * @author Celio Macedo Nº 8170358
 */
public class Jogo {
    
    
    private Mapa mapa;
    
    private double pontosVida;
    
    public Jogo(Mapa mapa){
        this.pontosVida=100;
        this.mapa=mapa;
    }
    
    public void modoManual() throws IOException, EmptyExcpetion{
        double pontosDeVida = this.pontosVida;
        int inimigoTmp = 0;
        String opcao = defineEntrada(); 
        String opcaotmp = null;
        UnorderedArrayList <String> caminhoPercorrido = new UnorderedArrayList<String>();
        Scanner scanner = new Scanner(System.in,"ISO-8859-1");
        
        if(!opcao.equals("-1")){
           caminhoPercorrido.addToRear(opcao); 
        }
        do{
            System.out.println("Divisao atual: "+ opcao + "\n");
            mapa.getDivisoes().mostraVerticesAdjacentes(opcao);
            System.out.println("Sair\n");
            
            while (opcaotmp!=opcao) {
                System.out.println("Escolha divisao para onde se quer mover: ");
                opcaotmp = scanner.nextLine();
                
                if(opcaotmp.equals("Sair")){
                    System.out.println("Saiu do Jogo!!\n");
                    opcao=opcaotmp;
                }else{
                   if(!verificaOpcao(opcao, opcaotmp).equals("invalido")){
                        opcao=opcaotmp;
                        
                    }else{
                        System.out.println("\nDivisao invalida, por favor tente outra vez!!\n");
                    } 
                }
                   
            }  
            opcaotmp=null;
            if(opcao.equals("Sair")){
                    pontosDeVida=0;
            }else{
                
                if(inimigoTmp==1){
                pontosDeVida=pontosDeVida-
                    this.mapa.getDivisoes().getEdgeWeight(opcao, caminhoPercorrido.last());
                }else if(this.mapa.getDivisoes().getEdgeWeight(opcao, caminhoPercorrido.last())>1 
                        && inimigoTmp==1){
                    inimigoTmp=0;
                }else if(this.mapa.getDivisoes().getEdgeWeight(opcao, caminhoPercorrido.last())>1 
                        && inimigoTmp==0){
                    inimigoTmp=1;
                }
                
                
                
                System.out.println(pontosDeVida+"\n");
                caminhoPercorrido.addToRear(opcao);
            }
            
            
        }while(pontosDeVida>0 && verificarSaidas(opcao)==null);
        
    }
    
    
    /**
     * Verifica se a divisao escolhida pelo utilizador para a proxima jogada é valida
     * @param opcao divisao em que se encontra o TO Cruz
     * @param opcaoTmp divisao para a qual o To Cruz se quer mover 
     * @return "invalido" caso a divisao escolhida nao seja valida e caso seja valida 
     * é retornado a opcao para onde o To Cruz se vai mover
     */
    public String verificaOpcao(String opcao, String opcaoTmp){
        
        Iterator itr = mapa.getDivisoes().obtemVerticesAdjacentes(opcao);
        
        while(itr.hasNext()){
            if(itr.next().equals(opcaoTmp)){
                return opcaoTmp;
            }
        }
        return "invalido";
    }
    
    /**
     * Este metodo vai verificar se o target existe como entrada no JSON
     * @param target target a procurar
     * @return o target se existir, se não existir, retorna null;
     * @throws IOException a
     * @throws ArrayIsEmpty 
     */
    private String verificarEntrada(String target) throws IOException, EmptyExcpetion{
        
        UnorderedArrayList<String> tempList = mapa.getEntradas();
        
        boolean tempBool = tempList.contains(target);
        
        if(tempBool==true){
            return target;
        }else{
            return null;
        }
    }
    
    /**
     * Este método vai imprimir todas as entradas no json.
     * @param mapa mapa onde queres imprimir as entradas.
     */
    private void imprimeEntradas(){
        Iterator iterator = this.mapa.getEntradas().iterator();
        
        System.out.println("Entradas possiveis:"+"\n");
        
        while(iterator.hasNext()){
            System.out.println("-> "+iterator.next());
        }
        System.out.println("\n");
        
    }
    
    
    /**
     * Metodo que vai pedir qual a entrada que o deseja que o Tó Cruz começe.
     * @return a entrada
     * @throws IOException 
     */
    public String defineEntrada() throws IOException, EmptyExcpetion{
 
        imprimeEntradas();
        Scanner scanner = new Scanner(System.in);
        String temp= "";
        
        do{
            System.out.println("Introduza a entrada na qual o Tó Cruz "
                  + "deve começar a missão: "+"\n");
            System.out.println("Para terminar selecione: -1"+"\n");
            
            temp = scanner.nextLine();

            if(temp.equals("-1")){
                System.out.println("Saiu do programa");
            }
            
            if(temp.equals(verificarEntrada(temp))){
                System.out.println("A entrada escolhida é "+temp+"\n");
            }
            
        }while(!temp.equals(verificarEntrada(temp)) && !temp.equals("-1"));
        return temp;

    }
    
    /**
     * Este metodo vai verificar se o target existe como saida no JSON
     * @param target a saida a verificar se existe
     * @return o target se existir, se não retorna null
     * @throws IOException
     * @throws ArrayIsEmpty 
     */
    private String verificarSaidas(String target) throws IOException, EmptyExcpetion{
        
        UnorderedArrayList<String> tempList = mapa.getSaidas();
        
        boolean tempBool = tempList.contains(target);
        
        if(tempBool==true){
            return target;
        }else{
            return null;
        }
    }
    
    /**
     * Este método vai correr o UnorderedArray com as saidas que estão no JSON e 
     * imprimi-las.
     * @param mapa mapa originado da leitura do JSON
     */ 
    private void imprimeSaidas(Mapa mapa){
        Iterator iterator = mapa.getSaidas().iterator();
        
        System.out.println("Saidas possiveis:");
        
        while(iterator.hasNext()){
            System.out.println("-> "+iterator.next());
        }
    }
    
     /**
    * Metodo que vai pedir qual a entrada que o deseja que o Tó Cruz acaba a sua missão.
    * @param mapa ficheiro JSON
    * @return a saida da missão.
    * @throws IOException
    * @throws ArrayIsEmpty 
    */
    
    public String defineSaida(Mapa mapa) throws IOException, EmptyExcpetion{
 
        imprimeSaidas(mapa);
        Scanner scanner = new Scanner(System.in);
        String temp= "";
        
        do{
            System.out.println("Introduza, corretamente a Saida na qual o Tó Cruz "
                  + "deve sair do edificio: "+"\n");
            System.out.println("Para terminar selecione: -1"+"\n");
            
            temp = scanner.nextLine();

            if(temp.equals("-1")){
                System.out.println("Saiu do programa.");
            }
            
            if(temp.equals(verificarEntrada(temp))){
                System.out.println("A saida escolhida é "+temp+"\n");
            }
            
        }while(!temp.equals(verificarSaidas(temp)) && !temp.equals("-1"));
        return temp;

    }
    
    
    
    public double getPontosVida(){
        return pontosVida;
    }

    
    
    
}
