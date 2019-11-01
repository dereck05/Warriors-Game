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
public class SalidaMutuaMessage extends AMessage {
    private String msg;
    private String jugador;
    private int id;
    public SalidaMutuaMessage(String topic,String j){
        super(topic);
        this.msg = "";
        this.jugador =j;
       
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

}
