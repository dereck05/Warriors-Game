/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Proxylog;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.time.LocalDateTime;
import java.util.ArrayList;



/**
 *
 * @author derec
 */
public class AtaqueCommand implements ICommand {
    private WarriorsSubscriber jugador;
    private ArrayList<Double> daño;
    private String topic;
    public AtaqueCommand(WarriorsSubscriber pJugador,ArrayList<Double> pDaño,String topic){
        this.jugador=pJugador;
        this.daño=pDaño;
        this.topic=topic;
    }
    @Override
    public void execute(){
        AtaqueMessage mensaje = new AtaqueMessage(this.topic,jugador.getId());
        mensaje.setDaño(daño);
        jugador.sendMessage(mensaje);    };
        
}
