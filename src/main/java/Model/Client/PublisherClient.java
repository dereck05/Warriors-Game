/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;



import java.io.IOException;
import java.util.Scanner;
import ServerImp.Message.PostMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;


public class PublisherClient {
    private WarriorsPublisher publisher;
    private static Scanner scan;
    
    public static void main(String[] args) throws InterruptedException {
        try {
            PublisherClient client = new PublisherClient();
            client.start();
            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
    }
    
    private void start() throws IOException, InterruptedException{
        scan = new Scanner(System.in);
        System.out.println("enter a topic");
        String topic = scan.nextLine();
        publisher = new WarriorsPublisher(topic, this);
        Thread.sleep(3000);

        if(!publisher.isConnected()){
            System.out.println("Couldnt connect...");
            System.exit(0);
        }

        run();
    }
    
    private void printMenu(){
        System.out.println("======================================");
        System.out.println("Publisher: " + publisher.getTopic());
        System.out.println("Opciones: ");
        System.out.println("1) Nuevo Post");
        System.out.println("2) Ver Posts");
        System.out.println("3) Ver Nivel");
        System.out.println("4) Darse de Baja");
        System.out.println("======================================");
    }
    
    private void run(){
        while(true){
            printMenu();
            String option = scan.nextLine();
            switch(option){
                case "1":
                    System.out.println("Ingrese el nuevo post: ");
                    String content = scan.nextLine();
                    publisher.buildPost(content);
                    break;
                case "2":
                    System.out.println(".....................Posts.......................");
                    int i = 0;
                    for(PostMessage post : publisher.getPosts()){
                        System.out.println(i + ")");
                        System.out.println(post.getContent());
                        System.out.println("Likes: " + post.getLikes());
                        System.out.println("Dislikes: " + post.getDislikes()); 
                        i++;
                    }
                    System.out.println("..................................................");
                    break;
                case "3":
                    System.out.println("-------------------------------------");
                    System.out.println("Subscribers: " + publisher.getSubscriberCount());
                    System.out.println("Nivel: " + publisher.getNivel());
                    System.out.println("-------------------------------------");
                    break;
                case "4":
                    System.out.println("Darse de baja? y/n");
                    String response = scan.nextLine();
                    if(response.equals("y")){
                       
                        publisher.quit();
                        System.exit(0);
                    }
                    break;
                default:
                    System.out.println("opción invalida");
            }
        }
       
    }
}
