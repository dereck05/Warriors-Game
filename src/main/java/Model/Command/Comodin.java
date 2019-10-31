/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import ServerImp.Message.ComodinMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.util.ArrayList;

/**
 *
 * @author derec
 */
public class Comodin  implements ICommand {
    private WarriorsSubscriber jugador;
    private ArrayList<Double> daño1;
    private ArrayList<Double> daño2;
    private String topic;
    public Comodin(WarriorsSubscriber juego,String topic,ArrayList<Double> daño1, ArrayList<Double> daño2) {
        this.topic=topic;
        this.jugador = juego;
        this.daño1=daño1;
        this.daño2=daño2;
    }

    public ArrayList<Double> getDaño1() {
        return daño1;
    }

    public void setDaño1(ArrayList<Double> daño1) {
        this.daño1 = daño1;
    }

    public ArrayList<Double> getDaño2() {
        return daño2;
    }

    public void setDaño2(ArrayList<Double> daño2) {
        this.daño2 = daño2;
    }
    
    @Override
    public void execute(){
        ComodinMessage mensaje = new ComodinMessage(this.topic,jugador.getId());
        mensaje.setDaño(daño1);
        mensaje.setDaño1(daño2);
        jugador.sendMessage(mensaje);
    };
    
}
