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
    
    /**
     * Este metodo vai verificar se o target existe como entrada no JSON
     * @param target target a procurar
     * @return o target se existir, se não existir, retorna null;
     * @throws IOException
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
    private void imprimeEntradas(Mapa mapa){
        Iterator iterator = mapa.getEntradas().iterator();
        
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
    public String defineEntrada(Mapa mapa) throws IOException, EmptyExcpetion{
 
        imprimeEntradas(mapa);
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
    
    
}
