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
import ServerImp.Message.ExitMessage;
import Model.Guerrero;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.ChatMessage;
import ServerImp.Message.ComodinMessage;
import ServerImp.Message.GanarMessage;
import ServerImp.Message.LogMessage;
import ServerImp.Message.PasarMessage;
import ServerImp.Message.RankingMessage;
import ServerImp.Message.ScoreMessage;
import ServerImp.Message.SalidaMutuaMessage;
import java.util.Scanner;

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
        if(message instanceof ExitMessage){
            ExitMessage m = (ExitMessage) message;
            if (m.getJugador().equals(this.getId())==false){
                System.out.println(m.getMensaje());
            }

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

                this.client.actual=true;

        }
        }if(message instanceof ComodinMessage){
            ComodinMessage m = (ComodinMessage) message;
            if (m.getJugador().equals(this.getId())==false){
                rebajarVida(m.getDaño());
                rebajarVida(m.getDaño1());
                evaluarPerdida(m.getTopic());

                this.client.actual=true;
            }
        }
        if(message instanceof LogMessage){
            LogMessage m = (LogMessage) message;
            if(m.getJugador().equals(this.getId())){
                this.client.logs = m.getLogs();
            }
        }
        if(message instanceof GanarMessage){
            GanarMessage m = (GanarMessage) message;
            if (m.getJugador().equals(this.getId())==false){
                System.out.println(m.getContent());
                client.getScore().addGane();
                ScoreMessage m1 = new ScoreMessage(m.getTopic(),this.getId());
                m1.setSocre(client.getScore());
                this.sendMessage(m1);
            }
        }
        if(message instanceof PasarMessage){
            PasarMessage m = (PasarMessage) message;
            if(m.getJugador().equals(this.getId())==false){
                System.out.println("El otro jugador a pasador de turno");
                client.actual=true;
            }
        }
        if (message instanceof RankingMessage){
            RankingMessage m = (RankingMessage) message;
            client.setRanking(m.getRanking());
        }
        if(message instanceof ScoreMessage){
            ScoreMessage m = (ScoreMessage) message;
            if(m.getSocre()==null){
              //  System.out.println("Pedir score");
                if(m.getJugador().equals(this.getId())==false){
                 //   System.out.println("Del otro jugador");
                    m.setSocre(client.getScore());
                    this.sendMessage(m);}
            }
            else{
               // System.out.println("Recibir score");
                if(m.getJugador().equals(this.getId())){
                  //  System.out.println("Al que lo pidió");
                    client.setScoreOtroJugador(m.getSocre());
                }
            }
        }
        if(message instanceof SalidaMutuaMessage){
            Scanner scan = new Scanner(System.in);
            SalidaMutuaMessage m = (SalidaMutuaMessage)message;
            if(m.getJugador().equals(this.getId())==false){
                switch(m.getId()){
                    case 1:
                        System.out.println(m.getMsg());
                        this.client.salidaMutua=true;
                        break;
                    case 2:
                        System.out.println(m.getMsg());
                        this.unsubscribe(m.getTopic());
                        break;
                    case 3:
                        
                        System.out.println(m.getMsg());
                        break;
                       
                
                }
                
                
            }
            
        }

    }

    @Override
    public void subscribe(String topic) {
        WarriorsRequestMessage m = new WarriorsRequestMessage();
        m.setRequestId(1);
        m.setRequestString(topic);
        m.setScore(client.getScore());


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

        if(client.getGuerreros().get(i).getVida()<=0){
            client.getGuerreros().get(i).setVida(0.0);
            client.getGuerreros().get(i).setActivo(false);
            client.getScore().addMuerte();
            System.out.println(client.getGuerreros().get(i).getNombre()+" ha muerto");
        }
        }
        if(porcentajeDaño>=100){
            client.getScore().addAtaqueExitoso();
        }
        else{
            client.getScore().addAtaqueFracasado();
        }
    }
    public void evaluarPerdida(String topic){
        boolean perdida = true;
        int i = 0;
        while (i < client.getGuerreros().size()){
            if(client.getGuerreros().get(i).getVida()>0){
                perdida = false;
            }
            i++;
        }
        if(perdida){
            client.getScore().addPerdida();
            System.out.println("Has perdido");
            GanarMessage m = new GanarMessage(topic,this.getId(),"Has ganado");
            this.sendMessage(m);
        }
        ScoreMessage m1 = new ScoreMessage(topic,this.getId());
        m1.setSocre(client.getScore());
        this.sendMessage(m1);
    }
}
