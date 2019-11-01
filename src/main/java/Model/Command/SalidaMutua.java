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
    private int id;
  
    public SalidaMutua(WarriorsSubscriber juego,String topic,String asunto,int id) {
        this.jugador = juego;
        this.topic=topic;
        this.asunto=asunto;
        this.id=id;
       
    }
    
    @Override
    public void execute(){
        SalidaMutuaMessage mensaje = new SalidaMutuaMessage(this.topic,this.jugador.getId());
        mensaje.setMsg(this.asunto);
        mensaje.setId(id);
        jugador.sendMessage(mensaje);
        
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
