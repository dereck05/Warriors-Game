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
    private WarriorsPublisher juego;
    private WarriorsSubscriber cliente;
    
    public SalidaMutua(WarriorsPublisher pJuego,WarriorsSubscriber cl){
        this.juego=pJuego;
        this.cliente = cl;
    }
    @Override
    public void execute(){};
    
}
