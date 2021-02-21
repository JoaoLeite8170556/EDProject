/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Colecoes.UnorderedArrayList;
import Excepcoes.EmptyExcpetion;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author celio
 */
public class Mapa {
    
    private String codMissao;
    private int versao;
    private final UnorderedArrayList<Inimigo> inimigos; 
    private final UnorderedArrayList<String> saidas;
    private final UnorderedArrayList<String> entradas;
    private Alvo alvo;
    private final NetworkJogo<String> divisoes;
    private String caminho;
    

    public Mapa(String caminho){   
        this.inimigos = new UnorderedArrayList<Inimigo>();
        this.saidas = new UnorderedArrayList<String>();
        this.entradas = new UnorderedArrayList<String>();
        this.divisoes = new NetworkJogo<String>();
        this.alvo  = null;
        this.caminho = caminho;
    }

    public String getCodMissao() {
        return codMissao;
    }
    public int getVersao() {
        return versao;
    }

    public UnorderedArrayList<Inimigo> getInimigos() {
        return inimigos;
    }

    public Mapa loadMapa(String caminho) throws FileNotFoundException, IOException, ParseException{
        
        Mapa mapa = new Mapa(caminho);
        JSONParser jSONParser = new JSONParser();
        FileReader reader = new FileReader(caminho);
        Object obj = jSONParser.parse(reader);
        
        JSONObject jsonObject = (JSONObject) obj;
        
        // Guardar o codigo da missao
        this.codMissao = (String) jsonObject.get("cod-missao");
        // guarda a versão do mapa
        this.versao = (int) (long) jsonObject.get("versao");
        
        JSONArray jsonSaidas = (JSONArray) jsonObject.get("saidas");
        
        // guarda as possiveis saidas
        for(int i=0;i<jsonSaidas.size();i++){
            this.saidas.addToRear(jsonSaidas.get(i).toString());
        }
        
        JSONArray jsonEntradas = (JSONArray) jsonObject.get("entradas");
        
        // guarda as possiveis entradas
        for(int i=0;i<jsonEntradas.size();i++){
            this.entradas.addToRear(jsonEntradas.get(i).toString());
        }
        
        JSONArray jsonDivisoes = (JSONArray) jsonObject.get("edificio");
        
        //guardar as divisões nos vertices do grafo
        for(int i=0;i<jsonDivisoes.size();i++){
            this.divisoes.addVertex(jsonDivisoes.get(i).toString());
        }
        JSONArray jsonInimigos = (JSONArray) jsonObject.get("inimigos");
        JSONArray jsonLigacoes = (JSONArray) jsonObject.get("ligacoes");
        
        for(Object ligacoes:jsonLigacoes){
            JSONArray edge = (JSONArray) ligacoes;
            if(retornaInimigo(edge.get(0).toString(), jsonInimigos)!=null){
                Inimigo ini =  retornaInimigo(edge.get(0).toString(), jsonInimigos);
                
                this.divisoes.addEdge(edge.get(0).toString(), 
                        edge.get(1).toString(), ini.getPoder());
                
            }else if(retornaInimigo(edge.get(1).toString(), jsonInimigos)!=null){
                Inimigo ini =  retornaInimigo(edge.get(1).toString(), jsonInimigos);
                
                this.divisoes.addEdge(edge.get(0).toString(), 
                        edge.get(1).toString(), ini.getPoder());
            }else{
                  this.divisoes.addEdge(edge.get(0).toString(), 
                        edge.get(1).toString(), 1);
            }
            
        }
        
        
        
        // guardar os inimigos que vão estar no mapa.
        
        for (Object inimigos : jsonInimigos) {
            
            String nome = ((JSONObject) inimigos).get("nome").toString();
            Integer poder = Integer.parseInt(((JSONObject)inimigos).get("poder").toString());
            String divisao =  ((JSONObject) inimigos).get("divisao").toString();
            Inimigo inimigo = new Inimigo(nome, poder, divisao);
            this.inimigos.addToRear(inimigo);
        }
        
        
        // guardar alvo.
        
        JSONObject jsonAlvo =  (JSONObject) jsonObject.get("alvo"); 

        Alvo alvo = retornaAlvo(jsonAlvo);

        this.alvo = alvo;
        
        return mapa;
    }
    
    
    
    
    public String retornaAlvo(){
        return this.getAlvos().getDivisao(); 
    }
    
    

    public UnorderedArrayList<String> getSaidas() {
        return saidas;
    }

    public UnorderedArrayList<String> getEntradas() {
        return entradas;
    }

    public Alvo getAlvos() {
        return alvo;
    }

    public NetworkJogo<String> getDivisoes() {
        return divisoes;
    }
    
    
    /***
     * Este metodo serve para retornar o alvo que esta decrito no JSON
     * @param jsonAlvo JSONObejct que vai corresponder ao Alvo
     * @return retorna um Alvo, do que foi retirado do ficheiro
     */
    
    private Alvo retornaAlvo(JSONObject jsonAlvo){

        Alvo alvoTemp = null;

        String divisao =  ((JSONObject) jsonAlvo).get("divisao").toString();
        String tipo = ((JSONObject) jsonAlvo).get("tipo").toString();

        alvoTemp= new Alvo(tipo, divisao);
        return alvoTemp;
    }
    
    /**
     * Este metodo vai retornar se o aposento existe como saida 
     * @param saida saida a procuar
     * @return retorna saida se ele existe, se não retorn string vazia;
     */
    public String retornaSaida(String saida){
        try {
            if(this.saidas.contains(saida)){
                return saida;
            }
        } catch (EmptyExcpetion ex){}
        return "";
    }
    
    /**
     * Este metodo vai verificar se o aposento existe como entrada
     * @param entrada entrada a procurar
     * @return retorna a saida se ela existir, senão retornar String vazia;
     */
    public String retornaEntrada(String entrada){
        try {
            if(this.entradas.contains(entrada)){
                return entrada;
            }
        } catch (EmptyExcpetion ex){}
        return "";
    }
    /**
     * Este metodo vai verificar se a divisao tem mais que um Inimigo
     * @param divisaoaux nome da divisao que se que verificar 
     * @param jsonInimigos  array onde estao todos os inimigos 
     * @return Inimigo caso haja inimigo na divisao 
     */
    
    private Inimigo retornaInimigo(String divisaoaux, JSONArray jsonInimigos){
        
  

        for (Object inimigos:jsonInimigos) {
            String nome = ((JSONObject) inimigos).get("nome").toString();
            Integer poder = Integer.parseInt(((JSONObject)inimigos).get("poder").toString());
            String divisao =  ((JSONObject) inimigos).get("divisao").toString();
            if(divisaoaux.equals(divisao)){
                
                // verificar se existe algum inimigo repetido 
                
                for (Object inimigosVerificacao:jsonInimigos) {
                    
                    String nomeVerificacao = ((JSONObject) inimigosVerificacao).get("nome").toString();
                    Integer poderVerificacao = Integer.parseInt(((JSONObject)inimigosVerificacao).get("poder").toString());
                    String divisaoVerificacao =  ((JSONObject) inimigosVerificacao).get("divisao").toString();
                    
                    if(divisao.equals(divisaoVerificacao) && !nome.equals(nomeVerificacao)){
                        poder = poder+poderVerificacao;
                    }
                    
                }
                Inimigo temp = new Inimigo(nome,(double)poder,divisao);
                
                return temp;
            }
        }
            
        return null;
    }
    
    /**
     * Este metodo vai verificar se a divisao tem algum inimigo
     * @param divisao divisao onde se quer verificar 
     * @return Inimigo se houver e null see nao ouver nenhum inimigo
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException 
     */
    
    public Inimigo verificaInimigo(String divisao) throws FileNotFoundException, IOException, ParseException{
        
        JSONParser jSONParser = new JSONParser();
        FileReader reader = new FileReader(this.caminho);
        Object obj = jSONParser.parse(reader);
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray jsonInimigos = (JSONArray) jsonObject.get("inimigos");
        
        Inimigo ini = null;
        
        for (Object inimigos:jsonInimigos) {
            String div =  ((JSONObject) inimigos).get("divisao").toString();
           
            if(div.equals(divisao)){
                ini = retornaInimigo(divisao, jsonInimigos);
            }
        }
        
        return ini;
    }
    
    
    private int inimigosRepetidos(String divisaoVerificar, String nomeVerificar, 
            JSONArray jsonInimigos){
        
    
        for (Object inimigos:jsonInimigos) {
            String nome = ((JSONObject) inimigos).get("nome").toString();
            Integer poder = Integer.parseInt(((JSONObject)inimigos).get("poder").toString());
            String divisao =  ((JSONObject) inimigos).get("divisao").toString();
             if(divisao.equals(divisaoVerificar) && !nome.equals(nomeVerificar)){
                        return poder;
                    }
            }
        return 0;
    }
}
