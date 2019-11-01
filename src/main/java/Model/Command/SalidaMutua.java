/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Message.AMessage;
import Model.Client.SubscriberClient;
import ServerImp.Message.ChatMessage;
import ServerImp.Message.ExitMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import ServerImp.Message.SalidaMutuaMessage;

/**
 *
 * @author derec
 */
public class SalidaMutua implements ICommand {
    private WarriorsSubscriber jugador;
    private String topic;
    private String asunto;
  
    public SalidaMutua(WarriorsSubscriber juego,String topic,String asunto) {
        this.jugador = juego;
        this.topic=topic;
        this.asunto=asunto;
       
    }
    
    @Override
    public void execute(){
        SalidaMutua mensaje = new SalidaMutua(this.jugador,this.topic,this.asunto);
        //jugador.sendMessage(mensaje);
        
    };

    public WarriorsSubscriber getJugador() {
        return jugador;
    }

    public void setJugador(WarriorsSubscriber jugador) {
        this.jugador = jugador;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    
    
}
