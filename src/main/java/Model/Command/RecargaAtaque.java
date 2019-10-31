/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Client.SubscriberClient;
import Model.Guerrero;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author derec
 */
public class RecargaAtaque implements ICommand {
    
    
    private String id;
    private WarriorsSubscriber jugador;
    
    public RecargaAtaque(WarriorsSubscriber subs,String ide){
        this.jugador=subs;
        this.id = ide;
     
    }

    @Override
    public void execute(){
        for(int i = 0 ; i < jugador.getJugador().getGuerreros().get(Integer.parseInt(this.id)-1).getAtaques().size();i++){
            jugador.getJugador().getGuerreros().get(Integer.parseInt(this.id)-1).getAtaques().get(i).setEstado("activa");
        }
        
        
    };
    
}
