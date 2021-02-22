/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import Excepcoes.EmptyExcpetion;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author celio
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException, EmptyExcpetion {
        Mapa mapa=new Mapa("./mapa1.json");
        
        mapa.loadMapa();
        Jogo jogo = new Jogo(mapa);
        
       jogo.modoManual();
        
        
    }
    
}
