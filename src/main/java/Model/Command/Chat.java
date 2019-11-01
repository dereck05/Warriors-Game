/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import ServerImp.Message.ChatMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;

/**
 *
 * @author derec
 */
public class Chat implements ICommand {
    private WarriorsSubscriber jugador;
    private String topic;
    private String asunto;
    private String content;
    public Chat(WarriorsSubscriber juego,String topic,String asunto,String content) {
        this.jugador = juego;
        this.topic=topic;
        this.asunto=asunto;
        this.content=content;
    }
    
    @Override
    public void execute(){
        ChatMessage c = new ChatMessage(topic, content,jugador.getId());
        c.setAsunto(asunto);
        jugador.sendMessage(c);
    };
    
}
