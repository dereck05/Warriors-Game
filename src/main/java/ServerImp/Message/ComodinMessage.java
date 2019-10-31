/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import java.util.ArrayList;

/**
 *
 * @author maryp
 */
public class ComodinMessage extends AMessage{
    private ArrayList<Double> daño;
    private ArrayList<Double> daño1;
    private String jugador;
    public ComodinMessage(String topic,String pJugador) {
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

    public ArrayList<Double> getDaño1() {
        return daño1;
    }

    public void setDaño1(ArrayList<Double> daño1) {
        this.daño1 = daño1;
    }
    
}
