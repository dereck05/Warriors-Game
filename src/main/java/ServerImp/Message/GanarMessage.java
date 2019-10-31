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
public class GanarMessage extends AMessage {
    String content;
    String jugador;
    public GanarMessage(String topic, String jugador,String content){
        super(topic);
        this.content=content;
        this.jugador=jugador;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    
}
