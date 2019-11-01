/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.ContentServer;

import ServerImp.Message.WarriorsConMessage;
import ContentServer.AContentServer;
import ContentServer.PublisherHandler;
import ContentServer.SubscriberHandler;
import Message.AMessage;
import ServerImp.Message.ExitMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.PostMessage;
import ServerImp.Message.WarriorsRequestMessage;
import ServerImp.Message.WarriorsTopicsMessage;


public class WarriorsContentServer extends AContentServer{
    
    public WarriorsContentServer() throws IOException{
        super();
        System.out.println("SERVER running");
    }

    @Override
    public void processSubMessage(AMessage message, SubscriberHandler handler) {
        if(message instanceof FeedMessage){
            FeedMessage m = (FeedMessage) message;
            
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof WarriorsRequestMessage){
            try {
                WarriorsRequestMessage m = (WarriorsRequestMessage) message;
                switch(m.getRequestId()){
                    //Jugadores en el juego
                    case 0: 
                        broadcastTopics(handler);
                        break;
                    //Unirse al juego
                    case 1:
                        String topic = m.getRequestString();
                        this.registerSubscription(topic, handler);
                        PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(topic)).findAny().orElse(null);
                        
                        WarriorsRequestMessage m2 = new WarriorsRequestMessage();
                        m2.setRequestId(1);
                        m2.setRequestString("Nuevo subscriptor");
                        publisher.sendMessage(m2);
                        
                        m2.setRequestId(0);
                        m2.setRequestString("Subscrito");
                        handler.sendMessage(m2);
                        break;
                    //Salir del juego
                    case 2: 
                        
                        topic = m.getRequestString();
                        this.removeSubscription(topic, handler);
                        publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(topic)).findAny().orElse(null);                       
                        WarriorsRequestMessage m3 = new WarriorsRequestMessage();
                        m3.setRequestId(2);
                        m3.setRequestString("Subscriptor Perdido");
                        publisher.sendMessage(m3);
                }
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof ExitMessage){
            try {
                
                ExitMessage m = (ExitMessage) message;
                m.setMensaje("El otro jugador se ha rendido.");
                this.broadcastMessageSub(m, m.getTopic());
                
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void processPubMessage(AMessage message, PublisherHandler handler) {
        if(message instanceof PostMessage){
            try {
                this.broadcastMessageSub(message, handler.getTopic());
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(message instanceof WarriorsRequestMessage){
            try {
                WarriorsRequestMessage m = (WarriorsRequestMessage) message;
                switch(m.getRequestId()){
                    case 4:
                        m.setRequestId(0);
                        m.setRequestString(m.getRequestString() + " se ha dado de baja");
                        this.broadcastMessageSub(m, handler.getTopic());
                        this.removePublisher(handler);
                        break;
                }
            }catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void broadcastTopics(SubscriberHandler handler) {
        WarriorsTopicsMessage m = new WarriorsTopicsMessage();
        for(String key : this.subscriptions.keySet()){
            m.getTopics().put(key, this.subscriptions.get(key).size());
        }
        try {
            handler.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void acceptPubConnection(PublisherHandler handler) {
        
        WarriorsConMessage m = new WarriorsConMessage(true);
        m.setId(handler.getTopic());
        m.setConnMessage("Conection Successful as Publisher");
        
        try {
            handler.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void acceptSubConnection(SubscriberHandler handler) {
        WarriorsConMessage m = new WarriorsConMessage(true);
        String newId = java.time.LocalDateTime.now().toString();
        handler.setId(newId);
        m.setId(newId);
        m.setConnMessage("Conection Successful as Subscriber");
        
        try {
            handler.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void denyPubConnection(PublisherHandler handler) {
        WarriorsConMessage m = new WarriorsConMessage(false);
        m.setId(handler.getTopic());
        m.setConnMessage("Conection Denied as Publisher");
        
        try {
            handler.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void denySubConnection(SubscriberHandler handler) {
        WarriorsConMessage m = new WarriorsConMessage(false);
        m.setConnMessage("Conection Denied as Publisher");
        
        try {
            handler.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
