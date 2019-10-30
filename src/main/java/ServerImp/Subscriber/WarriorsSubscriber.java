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


public class WarriorsSubscriber extends ASubscriber{
    private Jugador jugador;
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

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
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
        
        if(message instanceof PostMessage){
            
            PostMessage m = (PostMessage) message;
            boolean flag = false;
            for(int i = 0; i < this.feed.size(); i++){
                if(this.feed.get(i).getPost().getId().equals(m.getId())){
                    this.feed.get(i).setPost(m);
                    flag = true;
                    break;
                }  
            }
            if(!flag){
                this.feed.add(new FeedMessage(m));
                System.out.println("Nuevo post de " + m.getTopic() + ": " + m.getContent());
                return;
            }   
            System.out.println("Post actualizado de " + m.getTopic());
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
    
}
