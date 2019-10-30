/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.PostMessage;
import ServerImp.Subscriber.WarriorsSubscriber;


public class SubscriberClient {
    private  WarriorsSubscriber subscriber;
    private  Scanner scan;
    public  List<String> topics;
    private Jugador jugador;
    public static void main(String[] args) throws InterruptedException {
        try {
            SubscriberClient client = new SubscriberClient();
            client.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
    }
    private void start() throws IOException, InterruptedException{
        scan = new Scanner(System.in);
            
            
       // subscriber= new WarriorsSubscriber(this);
        Thread.sleep(3000);

        if(!subscriber.isConnected()){
            
            System.out.println("Couldnt connect...");
            System.exit(0);
        }
    //   this.jugador = new Jugador(subscriber.getId());
        
        run();
    }
    
    private void printMenu(){
        System.out.println("======================================");
        System.out.println("Subscriber: " + subscriber.getId());
        System.out.println("Opciones: ");
        System.out.println("1) Retar jugador: ");
        System.out.println("2) Crear guerrero: ");
        System.out.println("3) Crear armas: ");
        System.out.println("4) Feed");
        System.out.println("======================================");
    }
    
    private void run() throws InterruptedException{
        while(true){
            printMenu();
            String option = scan.nextLine();
            switch(option){
                case "1":
                    System.out.println("Publishers not subscribed to: ");
                    System.out.println("----------------------------------");
                    this.subscriber.askForTopics();
                    Thread.sleep(1000);
                    
                    for (String key : topics){
                        if(subscriber.getSubscriptions().contains(key)){
                            this.topics.remove(key);
                            break;
                        }
                        System.out.print(key);

                    }
                    System.out.println("-----------------------------------");
                    System.out.println("enter name of suscriber:");
                    String temp = scan.nextLine();
                    if(topics.contains(temp)){
                        subscriber.subscribe(temp);
                    }
                    break;
                case "2":
                    System.out.println(".....................Subscriptions.......................");
                    for (String topic : this.subscriber.getSubscriptions()){                      
                        System.out.println(topic);
                    }
                    System.out.println("..................................................");
                    System.out.println("enter name of publisher:");
                    String temp2 = scan.nextLine();
                    if(this.subscriber.getSubscriptions().contains(temp2)){
                        subscriber.unsubscribe(temp2);
                    }
                  
                    break;
                case "3":
                    System.out.println("-------------------------------------");
                    System.out.println("FEED");
                    System.out.println("-------------------------------------");
                    int i = 0;
                    for(FeedMessage post : this.subscriber.getFeed()){
                        System.out.println(i + ")");
                        System.out.println(post.getPost().getContent());
                        System.out.println("Likes: " + post.getPost().getLikes());
                        System.out.println("Dislikes: " + post.getPost().getDislikes());
                        
                        System.out.println("My Reaction: " + post.isLiked());
                        i++;
                    }
                    System.out.println("-------------------------------------");
                    System.out.println("React to one? y/n");
                    String temp3 = scan.nextLine();
                    if (temp3.equals("y")){
                        System.out.println("Which one?");
                        temp3 = scan.nextLine();
                        if(temp3.compareTo("0")>=0 && temp3.compareTo("" + i)<0){
                            int j = Integer.parseInt(temp3);
                            System.out.println("Like (l) or dislike(d)?");
                            temp3 = scan.nextLine();
                            boolean pliked = this.subscriber.getFeed().get(j).isLiked();
                            boolean preacted = this.subscriber.getFeed().get(j).isReacted();
                            if(temp3.equals("l")){
                                this.subscriber.getFeed().get(j).setLiked(true);
                                this.subscriber.getFeed().get(j).setReacted(true);
                            }
                            if(temp3.equals("d")){
                                this.subscriber.getFeed().get(j).setLiked(false);
                                this.subscriber.getFeed().get(j).setReacted(true);
                            }
                            if((this.subscriber.getFeed().get(j).isLiked() == !pliked || 
                                    preacted == false) && (temp3.equals("l") || temp3.equals("d"))){
                                
                                this.subscriber.getFeed().get(j).setInterchanged(preacted);
                                this.subscriber.sendMessage(this.subscriber.getFeed().get(j));
                            }
                        }
                        
                    }
                    break;
                
                default:
                    System.out.println("opción invalida");
            }
        }
       
    }
}
