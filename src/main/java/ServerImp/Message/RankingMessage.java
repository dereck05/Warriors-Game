/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import Model.Score;
import java.util.ArrayList;

/**
 *
 * @author maryp
 */
public class RankingMessage extends AMessage {
    ArrayList<Score> ranking;
    public RankingMessage(String topic) {
        super(topic);
    }

    public ArrayList<Score> getRanking() {
        return ranking;
    }

    public void setRanking(ArrayList<Score> ranking) {
        this.ranking = ranking;
    }
    
    
}
