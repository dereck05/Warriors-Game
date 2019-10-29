/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author derec
 */
public class Juego {
    private ArrayList<Jugador> jugadores;
    private ArrayList<String> mensajes;
    
    public Juego(ArrayList<Jugador> jugad){
        this.jugadores = jugad;
        this.mensajes = new ArrayList();
        
    }
    
    public void setJugadores(ArrayList<Jugador> jugad){
        this.jugadores = jugad;
    }
    public void addMensajes(String m){
        this.mensajes.add(m);
    }
    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }
    
    public ArrayList<String> getMensajes(){
        return this.mensajes;
    }
}
