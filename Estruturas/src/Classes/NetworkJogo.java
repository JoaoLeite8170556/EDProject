/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.GraphWeight;
import Colecoes.LinkedQueue;
import Colecoes.UnorderedArrayList;
import Excepcoes.EmptyExcpetion;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


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
    public Iterator <T> obtemVerticesAdjacentes(T vertex){
        
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
        return resultList.iterator();
    }
    /**
     * Imprime os vertices adjacentes a determinado vertice
     * @param vertex vertice a imprimir os vertices adjacentes
     */
    public void mostraVerticesAdjacentes(T vertex){
        Iterator itr = obtemVerticesAdjacentes(vertex);
        
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
    


    public Iterator<T> iteratorBFS(int startIndex) throws EmptyExcpetion
   {
       Integer x;
       LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
       UnorderedArrayList<T> resultList = new UnorderedArrayList<T>();

      if (validIndex(startIndex)==-1)
         return resultList.iterator();

      boolean[] visited = new boolean[numVertices];
      for (int i = 0; i < numVertices; i++)
         visited[i] = false;


      traversalQueue.enqueue(new Integer(startIndex));
      visited[startIndex] = true;

      while (!traversalQueue.isEmpty())
      {
          x = traversalQueue.dequeue();
          resultList.addToRear(vertices[x.intValue()]);
          /** Find all vertices adjacent to x that have not been
           * visited and queue them up */
          
          for (int i = 0; i < numVertices; i++)
          {
              if((weights[x.intValue()][i] < Double.POSITIVE_INFINITY)
                      && !visited[i])
              {
                  traversalQueue.enqueue(new Integer(i));
                  visited[i] = true;
              }
          }

      }
      return resultList.iterator();
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
