/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package estruturas;

import Colecoes.GraphWeight;
import Colecoes.UnorderedArrayList;


/**
 * 
 * Classe para realizar os metódos necessarios para o problema, do grafo pesado.
 * 
 * @author João Leite Nº 8170556 
 * @author Celio Macedo Nº 8170358
 */
public class NetworkJogo<T> extends GraphWeight<T>{
    
    /**
     * Este metodo vai returnar os vertices que estao ligados ao vertex
     * @param vertex 
     * @return  todos os vertices conectados.
     */
    public UnorderedArrayList<T> verticesVizinhos(T vertex){
        int index = getIndex(vertex);
        
        UnorderedArrayList<T> resultList = new UnorderedArrayList<>();
        
        for(int i=0;i<super.vertices.length;i++){
            if(this.weights[index][i] != Double.POSITIVE_INFINITY){
                resultList.addToRear(super.vertices[i]);
            }
        }
        return resultList;
    }
    
    @Override
    public void setEdgeWeight(T startVertex,T targetVertex,double weight){
        int startIndex = getIndex(startVertex);
        int targetIndex = getIndex(targetVertex);
        for(int i=0;i<this.vertices.length;i++){
            this.weights[startIndex][targetIndex]=weight;
            this.weights[targetIndex][startIndex]=weight;
            
        }
    }
    
    public void contaLigacoes(){
        int contador =0;

        for(int i=0;i<this.numVertices;i++){
            for(int j=0;j<this.numVertices;j++){
                if(this.weights[i][j]!=Double.POSITIVE_INFINITY){
                    contador = contador + 1;
                }
            }
        }
        System.out.println(contador);
    }
    
    
    @Override
    public String toString(){
        return super.toString();
    }
}
