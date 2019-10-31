/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Publisher;

import ContentServer.PublisherHandler;
import ServerImp.Message.WarriorsConMessage;
import ServerImp.Message.PostMessage;
import Message.AMessage;
import Publisher.APublisher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ServerImp.ContentServer.WarriorsContentServer;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.WarriorsRequestMessage;
import Model.Client.PublisherClient;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.ChatMessage;
import ServerImp.Message.ComodinMessage;
import ServerImp.Message.GanarMessage;


public class WarriorsPublisher extends APublisher{
    private ArrayList<String> logs;
    private int subscriberCount = 0;
   // private PublisherClient client;

    public WarriorsPublisher(String topic) throws IOException{
        super(topic);
        logs = new ArrayList();
        //this.client = client;
    }


    public int getSubscriberCount() {
        return subscriberCount;
    }

    public void addLog(String m){
        this.logs.add(m);
    }
    
    public ArrayList<String> getLogs(){
        return this.logs;
    }
    
    /*
    public void buildLog(){
        PostMessage newPost = new PostMessage(this.getTopic(), content);
        this.posts.add(newPost);
        publish(newPost);
    }*/
    
    public void quit(){
        try {
            WarriorsRequestMessage m = new WarriorsRequestMessage();
            m.setRequestId(4);
            m.setRequestString(this.getTopic());
            this.intermediate.sendMessage(m);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void publish(AMessage newPost) {
        try {
            this.intermediate.sendMessage(newPost);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void receivedMessage(AMessage message) {
       
        if(message instanceof WarriorsConMessage){
            WarriorsConMessage m = (WarriorsConMessage) message;
            this.setConnected(m.isAcceptedConnection());
            System.out.println(m.getConnMessage());
        }
        if(message instanceof AtaqueMessage || message instanceof ChatMessage || message instanceof ComodinMessage || message instanceof GanarMessage){
            this.publish(message);
        }

        //Aquí va toda la lógica
        /*
        if(message instanceof FeedMessage){
            FeedMessage m = (FeedMessage) message;
            PostMessage original = this.posts.stream().
                    filter(post -> post.getId().equals(m.getPost().getId())).findAny().orElse(null);
            
            
            if(m.isLiked()){
                original.setLikes(original.getLikes() + 1);
                if(m.isInterchanged()){
                    original.setDislikes(original.getDislikes() - 1);
                }
                if (original.getLikes() % 10 == 0){
                    this.buildPost("Mi Post " + original.getId() + " ha alcanzado " + original.getLikes() + " likes!");
                }
            }
            else{
                original.setDislikes(original.getDislikes() + 1);
                if(m.isInterchanged()){
                    original.setLikes(original.getLikes() - 1);
                }
                if (original.getDislikes() % 10 == 0){
                    this.buildPost("Mi Post " + original.getId() + " ha alcanzado " + original.getDislikes() + " dislikes!");
                }
            }
            try {
                this.intermediate.sendMessage(original);
            } catch (IOException ex) {
                Logger.getLogger(WarriorsPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        */
        //Suscriptores al juego
        if(message instanceof WarriorsRequestMessage){
            WarriorsRequestMessage m = (WarriorsRequestMessage) message;
            System.out.println(m.getRequestString());
            switch(m.getRequestId()){
                
                case 1:
                    subscriberCount++; 
                    break;
                case 2:                      
                    subscriberCount--;
                    break;

                    
                    
                    
            }
        }
        
    }
    
}
