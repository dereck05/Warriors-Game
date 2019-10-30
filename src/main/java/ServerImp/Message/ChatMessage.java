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
public class ChatMessage extends AMessage{
    private String id;
    private String content;
    public ChatMessage(String topic, String content) {
        super(topic);
        this.content=content;
        this.id = java.time.LocalDateTime.now().toString();
        
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
