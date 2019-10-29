/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class WarriorsTopicsMessage extends AMessage{
    private Map<String, Integer> topics;
    
    public WarriorsTopicsMessage(){
        super("Publishers");
        topics = new HashMap();
    }

    public Map<String, Integer> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, Integer> topics) {
        this.topics = topics;
    }
    
    
}
