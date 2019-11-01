/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Client.SubscriberClient;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.ExitMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author derec
 */
public class Rendirse implements ICommand {
    
    private WarriorsSubscriber jugador;
    private String topic;
    private String mensaje;
    public Rendirse(WarriorsSubscriber pJugador,String topic){
        this.jugador=pJugador;
        this.topic=topic;
        this.mensaje = "";
    }
    @Override
    public void execute(){
        ExitMessage mensaje = new ExitMessage(this.topic,this.jugador.getClient().getID());
        jugador.sendMessage(mensaje);
        jugador.unsubscribe(topic);
        
    };
    
}
