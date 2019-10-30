/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import Model.Ataque;
import Model.Guerrero;

/**
 *
 * @author maryp
 */
public class AtaqueMessage extends AMessage {
    private Guerrero guerrero;
    private Ataque arma;
    public AtaqueMessage(String topic) {
        super(topic);
    }

    public Guerrero getGuerrero() {
        return guerrero;
    }

    public void setGuerrero(Guerrero guerrero) {
        this.guerrero = guerrero;
    }

    public Ataque getArma() {
        return arma;
    }

    public void setArma(Ataque arma) {
        this.arma = arma;
    }
    
    
}
