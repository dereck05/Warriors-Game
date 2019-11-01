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
public class LogMessage extends AMessage{
    private ArrayList<String> logs;
    private String jugador;
    public LogMessage(String topic, String jugador) {
        super(topic);
        logs = new ArrayList<String>();
        this.jugador=jugador;
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public void setLogs(ArrayList<String> logs) {
        this.logs = logs;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }
    
}
