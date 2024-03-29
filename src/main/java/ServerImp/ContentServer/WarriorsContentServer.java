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
import Model.Score;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.ChatMessage;
import ServerImp.Message.ComodinMessage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.GanarMessage;
import ServerImp.Message.LogMessage;
import ServerImp.Message.PasarMessage;
import ServerImp.Message.PostMessage;
import ServerImp.Message.RankingMessage;
import ServerImp.Message.SalidaMutuaMessage;
import ServerImp.Message.ScoreMessage;
import ServerImp.Message.WarriorsRequestMessage;
import ServerImp.Message.WarriorsTopicsMessage;
import java.util.ArrayList;
import java.util.HashMap;


public class WarriorsContentServer extends AContentServer{
    private HashMap<SubscriberHandler, Score> ranking;
    public WarriorsContentServer() throws IOException{
        super();
        ranking = new HashMap<>();
        System.out.println("SERVER running");
    }

    @Override
    public void processSubMessage(AMessage message, SubscriberHandler handler) {
        if(message instanceof SalidaMutuaMessage){
            SalidaMutuaMessage m = (SalidaMutuaMessage) message;
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        if(message instanceof RankingMessage){
            RankingMessage m = (RankingMessage) message;
            ArrayList<Score> rank = new ArrayList<Score>();
            for(Score value: ranking.values()){
                rank.add(value);
            }
            m.setRanking(rank);
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof ScoreMessage){
            ScoreMessage m = (ScoreMessage) message;
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                this.ranking.replace(handler, m.getSocre());
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof AtaqueMessage){
           // System.out.println("Si entra");
            AtaqueMessage m = (AtaqueMessage) message;

            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof GanarMessage){
            GanarMessage m = (GanarMessage) message;
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof LogMessage){
            LogMessage m = (LogMessage) message;
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof PasarMessage){
            PasarMessage m = (PasarMessage) message;
            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(message instanceof ChatMessage){
            ChatMessage m = (ChatMessage) message;

            PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (message instanceof ComodinMessage){
            ComodinMessage m = (ComodinMessage) message;

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
                        ranking.put(handler, m.getScore());
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
          

                ExitMessage m = (ExitMessage) message;
                m.setMensaje("El otro jugador se ha rendido.");
                
                PublisherHandler publisher = this.publishers.stream().filter(pub -> pub.getTopic().equals(m.getTopic())).findAny().orElse(null);
            try {
                publisher.sendMessage(m);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsContentServer.class.getName()).log(Level.SEVERE, null, ex);
            }


        }
    }

    @Override
    public void processPubMessage(AMessage message, PublisherHandler handler) {
        if(message instanceof AtaqueMessage || message instanceof ComodinMessage || message instanceof ChatMessage || message instanceof GanarMessage || message instanceof PasarMessage || message instanceof LogMessage || message instanceof ScoreMessage || message instanceof RankingMessage|| message instanceof SalidaMutuaMessage || message instanceof ExitMessage){
            try {
                // System.out.println("???");
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
