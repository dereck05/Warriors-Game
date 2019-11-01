/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import Model.Score;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author maryp
 */
public class ScoreMessage extends AMessage {
    private String jugador;
    private Score socre;
    public ScoreMessage(String topic, String jugador) {
        super(topic);
        this.jugador=jugador;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public Score getSocre() {
        return socre;
    }

    public void setSocre(Score socre) {
        this.socre = socre;
    }
    
}
