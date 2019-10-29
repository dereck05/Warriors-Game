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


public class WarriorsPublisher extends APublisher{
    private List<PostMessage> posts;
    private int subscriberCount = 0;
    private int nivel = 0;
    private PublisherClient client;

    public WarriorsPublisher(String topic, PublisherClient client) throws IOException{
        super(topic);
        posts = new ArrayList();
        this.client = client;
    }

    public List<PostMessage> getPosts() {
        return posts;
    }

    public int getSubscriberCount() {
        return subscriberCount;
    }

    public int getNivel() {
        return nivel;
    }
    
    
    
    public void buildPost(String content){
        PostMessage newPost = new PostMessage(this.getTopic(), content);
        this.posts.add(newPost);
        publish(newPost);
    }
    
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
        
        if(message instanceof WarriorsRequestMessage){
            WarriorsRequestMessage m = (WarriorsRequestMessage) message;
            System.out.println(m.getRequestString());
            switch(m.getRequestId()){
                
                case 1:
                    subscriberCount++;
                    if(subscriberCount % 10 == 0){
                        nivel++;
                        //mandar mensaje
                    }
                        
                    break;
                case 2:
                    if(subscriberCount % 10 == 0){
                        nivel--;
                        //mandar mensaje
                    }
                        
                    subscriberCount--;
                    break;
                case 3: 
                    PostMessage reacted = this.posts.stream()
                                            .filter(post -> post.getId()
                                            .equals(m.getRequestString())).findAny().orElse(null);
                    
                    
                    
            }
        }
        
    }
    
}
