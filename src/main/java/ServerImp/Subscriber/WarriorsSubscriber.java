/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Subscriber;

import Message.AMessage;
import Subscriber.ASubscriber;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.PostMessage;
import ServerImp.Message.WarriorsConMessage;
import ServerImp.Message.WarriorsRequestMessage;
import ServerImp.Message.WarriorsTopicsMessage;
import Model.Client.SubscriberClient;
import Model.Client.Jugador;
import Model.Guerrero;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.ChatMessage;
import ServerImp.Message.ComodinMessage;
import ServerImp.Message.GanarMessage;


public class WarriorsSubscriber extends ASubscriber{
    private List<FeedMessage> feed;
    Jugador client;
    
    public WarriorsSubscriber(Jugador client) throws IOException{
        super();
        this.feed = new ArrayList();
        this.client = client;
    }

    public List<FeedMessage> getFeed() {
        return feed;
    }





    public Jugador getClient() {
        return client;
    }

    public void setClient(Jugador client) {
        this.client = client;
    }
    
    
    
    @Override
    public void askForTopics(){
        WarriorsRequestMessage m = new WarriorsRequestMessage();
        m.setRequestId(0);
        
        sendMessage(m);
    }
    
    @Override
    public void sendMessage(AMessage message) {
        try {
            this.intermediate.sendMessage(message);
        } catch (IOException ex) {
            Logger.getLogger(WarriorsSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void receivedMessage(AMessage message) {
        //Conección
        if(message instanceof WarriorsConMessage){
            WarriorsConMessage m = (WarriorsConMessage) message;
            this.setConnected(m.isAcceptedConnection());
            System.out.println(m.getConnMessage());
            System.out.println(m.getId());
            if(this.isConnected())
                this.setId(m.getId());
        }
        //Jugadores
        if(message instanceof WarriorsTopicsMessage){
            WarriorsTopicsMessage m = (WarriorsTopicsMessage) message;
            this.client.topics = m.getTopics();

            
        }
        //Jugadores
        if(message instanceof WarriorsRequestMessage){
            WarriorsRequestMessage m = (WarriorsRequestMessage) message;
            System.out.println(m.getRequestString());
            switch(m.getRequestId()){
                case 0:
                    
                    break;
                
                    
            }
        }
        if (message instanceof ChatMessage){
            ChatMessage m = (ChatMessage) message;
            if(m.getJugador().equals(this.getId())==false){
                System.out.println(m.getAsunto());
                this.client.mensajes.put(m.getJugador(), m.getContent());
            }
        }
        
        if(message instanceof AtaqueMessage){
           // Fuego-Aire-Agua-Magia blanca-Magia negra-Electricidad-Hielo-Acid-Espiritualidad-Hierro
            AtaqueMessage m = (AtaqueMessage) message;
            if(m.getJugador().equals(this.getId())==false){
                rebajarVida(m.getDaño());
                evaluarPerdida(m.getTopic());

            }
        }if(message instanceof ComodinMessage){
            ComodinMessage m = (ComodinMessage) message;
            if (m.getJugador().equals(this.getId())==false){
                rebajarVida(m.getDaño());
                rebajarVida(m.getDaño1());
                evaluarPerdida(m.getTopic());
            }
        }
        if(message instanceof GanarMessage){
            GanarMessage m = (GanarMessage) message;
            if (m.getJugador().equals(this.getId())==false){
                System.out.println(m.getContent());
                client.getScore().addGane();
            }
        }
        
    }

    @Override
    public void subscribe(String topic) {
        WarriorsRequestMessage m = new WarriorsRequestMessage();
        m.setRequestId(1);
        m.setRequestString(topic);
        
        
        sendMessage(m);
        
        this.addSubscription(topic);
    }

    @Override
    public void unsubscribe(String topic) {
        this.subscriptions.remove(topic);
        WarriorsRequestMessage m = new WarriorsRequestMessage();
        m.setRequestId(2);
        m.setRequestString(topic);
        try {
            this.intermediate.sendMessage(m);
            
        } catch (IOException ex) {
            Logger.getLogger(WarriorsSubscriber.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void rebajarVida(ArrayList<Double> daño){
        Double porcentajeDaño = 0.0;
        for(int i=0; i<client.getGuerreros().size();i++){
        Double rebaja;                   
        Double vidaActual = client.getGuerreros().get(i).getVida();
        switch(client.getGuerreros().get(i).getTipo()){

            case "fuego":   
                rebaja = daño.get(0);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "aire":
                rebaja = daño.get(1);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "agua":
                rebaja = daño.get(2);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "magia blanca":
                rebaja = daño.get(3);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "magia negra":
                rebaja = daño.get(4);
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "electricidad":
                rebaja = daño.get(5);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "hielo":
                rebaja = daño.get(6);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "acid":
                rebaja = daño.get(7);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "espiritualidad":
                rebaja = daño.get(8);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;
            case "hierro":
                rebaja = daño.get(9);
                porcentajeDaño += rebaja;
                client.getGuerreros().get(i).setVida(vidaActual-rebaja);
                break;

        }
        if(porcentajeDaño>=100){
            client.getScore().addAtaqueExitoso();
        }
        else{
            client.getScore().addAtaqueFracasado();
        }
        if(client.getGuerreros().get(i).getVida()<=0){
            client.getGuerreros().get(i).setVida(0.0);
            client.getGuerreros().get(i).setActivo(false);
            client.getScore().addMuerte();
            System.out.println(client.getGuerreros().get(i).getNombre()+" ha muerto");
        }
        }
    }
    public void evaluarPerdida(String topic){
        boolean perdida = true;
        int i = 0;
        while (perdida){
            if(client.getGuerreros().get(i).getVida()>0){
                perdida = false;
            }
        }
        if(perdida){
            client.getScore().addPerdida();
            System.out.println("Has perdido");
            GanarMessage m = new GanarMessage(topic,this.getId(),"Has ganado");
            this.sendMessage(m);
        }
    }
}
