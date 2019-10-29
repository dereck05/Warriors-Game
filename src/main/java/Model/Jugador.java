/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author derec
 */
public class Jugador {
    private int id;
    private ArrayList<Guerrero> guerreros;
    private Score score;
    private String status;
    
    
    public Jugador(int i,ArrayList<Guerrero> gue){
        this.id = i;
        this.guerreros = gue;
        this.score = new Score();
        this.status = "activo";
    }
    
    public void setID(int i){
        this.id = i;
    }
    public void setGuerreros(ArrayList<Guerrero> gue){
        this.guerreros = gue;
    }
    public void setScore(Score s){
        this.score = s;
    }
    public void setStatus(String s){
        this.status = s;
    }
    public int getID(){
        return this.id;
    }
    public ArrayList<Guerrero> getGuerreros(){
        return this.guerreros ;
    }
    public Score getScore(){
        return this.score;
    }
    public String getStatus(){
        return this.status;
    }
}
