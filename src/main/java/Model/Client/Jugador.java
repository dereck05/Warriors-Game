/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;

import Model.Guerrero;
import Model.Score;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author derec
 */
public class Jugador {
    private WarriorsPublisher publisher;
    private WarriorsSubscriber subscriber;
    private String id;
    private ArrayList<Guerrero> guerreros;
    private Score score;
    private String status;
    public  Map<String,Integer> topics;
    public Jugador(String i, WarriorsPublisher pPublisher){
        this.id=i;
        this.score=new Score();
        this.publisher= pPublisher;
        this.status = "activo";
    }
    public Jugador(String i,ArrayList<Guerrero> gue){
        this.id = i;
        this.guerreros = gue;
        this.score = new Score();
        this.status = "activo";
    }
    
    public void setID(String i){
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
    public String getID(){
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
    private void start() throws IOException, InterruptedException{         
        subscriber= new WarriorsSubscriber(this);
        Thread.sleep(3000);

        if(!subscriber.isConnected()){
            
            System.out.println("Couldnt connect...");
            System.exit(0);
        }
        subscriber.subscribe(publisher.getTopic());
    //   this.jugador = new Jugador(subscriber.getId());
        
     //   run();
    }
    
}
