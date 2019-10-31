/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import Model.Ataque;
import Model.Guerrero;
import java.util.ArrayList;

/**
 *
 * @author maryp
 */
public class AtaqueMessage extends AMessage {
    private ArrayList<Double> daño;
    private String jugador;
    public AtaqueMessage(String topic,String pJugador) {
        super(topic);
        this.jugador=pJugador;
    }

    public ArrayList<Double> getDaño() {
        return daño;
    }

    public void setDaño(ArrayList<Double> daño) {
        this.daño = daño;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    

    
    
}
