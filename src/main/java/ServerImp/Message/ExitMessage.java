/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;

/**
 *
 * @author derec
 */
public class ExitMessage extends AMessage {
    private String jugador;
    private String mensaje;

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public ExitMessage(String topic,String pJugador) {
        super(topic);
        this.jugador=pJugador;
        
        this.mensaje = "";
    }
}
