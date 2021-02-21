/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.GraphWeight;
import Colecoes.UnorderedArrayList;
import Excepcoes.EmptyExcpetion;
import java.util.Iterator;


/**
 * 
 * Classe para realizar os metódos necessarios para o problema, do grafo pesado.
 * 
 * @author João Leite Nº 8170556 
 * @author Celio Macedo Nº 8170358
 */
public class NetworkJogo<T> extends GraphWeight<T>{
    
    /**Construtor da Network do jogo*/
    public NetworkJogo(){
        super();
    }
    
    /**
     * Metodo que vai guardar num  UnorderedArrayList os vertices adjacentes
     * a determinado vertice
     * @param vertex vertice a verificar seus vizinhos
     * @return a lista com os vertices
     */
    public UnorderedArrayList<T> obtemVerticesAdjacentes(T vertex){
        
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();
        
        int index = getIndex(vertex);
        
        for(int i=0;i<this.numVertices;i++){
            if(this.weights[i][index]!=Double.POSITIVE_INFINITY){
                resultList.addToRear(this.vertices[i]);
            }
        }
        for(int i=0;i<this.numVertices;i++){
            try {
                if(this.weights[index][i]!=Double.POSITIVE_INFINITY
                        && !resultList.contains(this.vertices[i])){
                    resultList.addToRear(this.vertices[i]);
                }
            } catch (EmptyExcpetion ex){}
        }
        return resultList;
    }
    /**
     * Imprime os vertices adjacentes a determinado vertice
     * @param vertex vertice a imprimir os vertices adjacentes
     */
    public void mostraVerticesAdjacentes(T vertex){
        Iterator itr = (Iterator) obtemVerticesAdjacentes(vertex);
        
        while(itr.hasNext()){
            System.out.println(itr.next());
        }
        
    }
    /**
     * Metodo que contava as ligações na matriz.
     */
    public void contaLigacoes(){
        int contador =0;
        
        for(int i=0;i<this.weights.length;i++){
            for(int j=0;j<this.weights.length;j++){
                if(this.weights[i][j]!=Double.POSITIVE_INFINITY && this.weights[j][i]!=Double.POSITIVE_INFINITY){
                    contador = contador + 1;
                }
            }
        }
        System.out.println(contador);
    }
    /**
     * Método para alterar o peso da ligação entre dois vertices.
     * @param startVertex vertice inicial
     * @param targetVertex vertice adjacente
     * @param weight novo peso a inserir
     */
    @Override
    public void setEdgeWeight(T startVertex,T targetVertex,double weight){
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        for(int i=0;i<this.vertices.length;i++){
            this.weights[startIndex][targetIndex]=weight;
            this.weights[targetIndex][startIndex]=weight;
            
        }
    }
    
   
    
    /**
     * Metodo que vai retornar uma string onde vai indicar uma matriz com as 
     * ligações e os vertices com as suas ligações correspondentes.
     * @return a string com os vertices e as suas ligaçoes
     */

    @Override
    public String toString(){
        return super.toString();
    }
}
