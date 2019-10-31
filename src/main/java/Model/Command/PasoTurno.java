/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import ServerImp.Message.PasarMessage;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author derec
 */
public class PasoTurno implements ICommand {
    private WarriorsSubscriber jugador;
    private String topic;

    public PasoTurno(WarriorsSubscriber jugador, String topic) {
        this.jugador = jugador;
        this.topic = topic;
    }
    
    
    @Override
    public void execute(){
        PasarMessage mensaje = new PasarMessage(this.topic,jugador.getId());
        jugador.sendMessage(mensaje);  
    };
    
}
