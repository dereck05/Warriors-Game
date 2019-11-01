/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Client.SubscriberClient;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author derec
 */
public class SalidaMutua implements ICommand {
    private WarriorsSubscriber jugador;
    private String topic;
    private String mensaje;
    public SalidaMutua(WarriorsSubscriber pJugador,String topic){
        this.jugador=pJugador;
        this.topic=topic;
        this.mensaje = "";
    }
    @Override
    public void execute(){
        
    };
    
}
