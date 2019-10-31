/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;

/**
 *
 * @author maryp
 */
public class PasarMessage extends AMessage {
    private String jugador;

    public PasarMessage(String topic, String jugador) {
        super(topic);
        this.jugador=jugador;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
     
}
