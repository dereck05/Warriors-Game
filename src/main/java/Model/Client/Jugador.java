/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;

import Model.Ataque;
import Model.Command.AtaqueCommand;
import Model.Command.ICommand;
import Model.Command.Invoker;
import Model.Command.RecargaAtaque;
import Model.Command.SeleccionarJugador;
import Model.Guerrero;
import Model.Personaje;
import Model.Score;
import Model.SuperFactory;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.FeedMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author derec
 */
public class Jugador {
    public WarriorsPublisher publisher;
    private WarriorsSubscriber subscriber;
    private String id;
    private ArrayList<Guerrero> guerreros;
    private Score score;
    private String status;
    private  Scanner scan;
    private String topic;
    public  Map<String,Integer> topics;
    
    
    
    public static void main(String[] args) throws InterruptedException {
        try {
            Jugador client = new Jugador();
            client.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Jugador(){
        this.score= new Score();
        this.guerreros = new ArrayList<Guerrero>();
        this.status="activo";
    }
    public Jugador(String i, WarriorsPublisher pPublisher){
        this.id=i;
        this.score=new Score();
        this.guerreros = new ArrayList<Guerrero>();
        this.publisher= pPublisher;
        this.status = "activo";
    }
    public Jugador(String i){
        this.id = i;
        this.guerreros = new ArrayList<Guerrero>();
        this.score = new Score();
        this.status = "activo";
    }
    
    public void setID(String i){
        this.id = i;
    }
    public void setGuerreros(ArrayList<Guerrero> gue){
        this.guerreros = gue;
    }
    public void setScore(Score s){
        this.score = s;
    }
    public void setStatus(String s){
        this.status = s;
    }
    public String getID(){
        return this.id;
    }
    public ArrayList<Guerrero> getGuerreros(){
        return this.guerreros ;
    }
    public Score getScore(){
        return this.score;
    }
    public String getStatus(){
        return this.status;
    }
    private void start() throws IOException, InterruptedException{     
        
        scan = new Scanner(System.in);
        subscriber= new WarriorsSubscriber(this);
        Thread.sleep(3000);

        if(!subscriber.isConnected()){
            
            System.out.println("Couldnt connect...");
            System.exit(0);
        }
      //  subscriber.subscribe(publisher.getTopic());
        
       this.setID(subscriber.getId());
        
        run();
    }
    
   private void printMenu(){
        System.out.println("======================================");
        System.out.println("Subscriber: " + subscriber.getId());
        System.out.println("Opciones: ");
        System.out.println("crear guerreros");
        System.out.println("crear juego nuevo");
        System.out.println("unirse a juego");
        System.out.println("atacar");
        System.out.println("seleccionar jugador");
        System.out.println("rendirse");
        System.out.println("pasar");
        System.out.println("salida mutua");
        System.out.println("enviar mensaje");
        System.out.println("recargar");    
        System.out.println("======================================");
   
   }
   private void run() throws InterruptedException{
       Invoker invoker = new Invoker();
       ICommand comando;
       String temelegido;
        while(true){
            printMenu();
            String option = scan.nextLine();
            switch(option){
                //Crear guerreros
                case "crear guerreros":
                    SuperFactory sf = new SuperFactory();
                    
                    System.out.println("Debe crear 4 guerreros");
                    for (int i=1; i<=2;i++){
                        System.out.println("-----------------------------------");
                        System.out.println("Ingrese el nombre del guerrero: ");
                        String tempNombre = scan.nextLine();
                        System.out.println("Ingrese el tipo del guerrero: ");
                        String tempTipo = scan.nextLine();
                        System.out.println("Ingrese el path con la  imagen del guerrero: ");
                        String tempPath = scan.nextLine();
                        ArrayList<Ataque> tempAtaque = new ArrayList<Ataque>();
                        for(int j=1; j<=1;j++){
                            System.out.println("Ingrese el nombre del arma: ");
                            String tempNombreArma = scan.nextLine();
                            System.out.println("Ingrese el path con la imagen del arma: ");
                            String tempImagenArma = scan.nextLine();
                            Ataque a=sf.crearAtaque(false, tempNombre, 0.0, 0.0, 0.0, 0.0,tempImagenArma , "activa");
                            tempAtaque.add(a);
                        }
                        Personaje p = sf.createPersonaje(true, tempNombre, tempPath, 100.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, tempAtaque, tempTipo);
                        Guerrero g = new Guerrero(p.getNombre(),p.getImage(),p.getVida(),p.getGolpesxTiempo(),p.getNivel(),p.getCampoAccion(),p.getNivelAparicion(),p.getCosto(),p.getX(),p.getY(),p.getAtaques(),p.getTipo());
                        g.setActivo(true);
                        g.setDamage();
                        guerreros.add(g);
                    }
                    System.out.println("Guerreros creados con éxito");
                   
                    break;
                //Crear juego nuevo
                case "crear juego nuevo":
                    try{
                    System.out.println("Ingrese el nombre del juego: ");
                    this.topic = scan.nextLine();
                    publisher = new WarriorsPublisher(topic);
                    Thread.sleep(3000);
                    if(!publisher.isConnected()){
                        System.out.println("No se pudo crear el juego");
                        System.exit(0);
                    }else{
                        subscriber.subscribe(topic);
                        System.out.println("Juego creado con éxito");
                    }
                    }
                    catch(Exception e){
                        System.out.println("No se pudo crear el juego");
                    }
                    
                  
                    break;
                //Unirse a juego existente
                case "unirse a juego":
                    System.out.println("Juegos disponibles: ");
                    System.out.println("----------------------------------");
                    this.subscriber.askForTopics();
                    Thread.sleep(1000);
                    
                    for (String key : topics.keySet()){
                        if(subscriber.getSubscriptions().contains(key)){
                            this.topics.remove(key);
                            break;
                        }
                        System.out.print("Juego: "+key);
                    }
                    System.out.println("-----------------------------------");
                    System.out.println("Ingrese el nombre del juego al que se desea unir:");
                    String temp = scan.nextLine();
                    if(topics.containsKey(temp)){
                        subscriber.subscribe(temp);
                        this.topic=temp;
                        
                    }
                  
                    /*System.out.println("-------------------------------------");
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
                        
                    }*/
                    break;
                case "atacar":
                    System.out.println("Escoja el numero del guerrero que desea enviar a atacar: ");
                    for(int i=0; i<guerreros.size();i++){
                        System.out.println(i+1+" "+guerreros.get(i).toString());
                    }
                    temelegido = scan.nextLine();
                    try{
                        Integer elegido = Integer.parseInt(temelegido);
                        Guerrero guerrero = guerreros.get(elegido-1);
                        System.out.println("Escoje el número de arma que desea usar");
                        for(int i=0; i<guerrero.getAtaques().size();i++){
                            System.out.println(i+1+" "+guerrero.getAtaques().get(i));
                        }
                        String temAElegida = scan.nextLine();
                        try{
                            Integer elegida = Integer.parseInt(temAElegida);
                            ArrayList<Double> daño = guerrero.getDamage().get(elegida-1);

                            comando = new AtaqueCommand(subscriber,daño,this.topic);
                            invoker.execute(comando);
                        }
                        catch(Exception e){
                            System.out.println("Debe escoger entre las opciones anteriores");
                        }
                    }
                    catch(Exception e){
                        System.out.println("Debe escoger entre las opciones anteriores");
                    }
                    break;
                case "seleccionar jugador":
                    System.out.println("Escoja el numero del guerrero que desea ver: ");
                    for(int i=0; i<guerreros.size();i++){
                        System.out.println(i+1+" "+guerreros.get(i).getNombre());
                    }
                    temelegido = scan.nextLine();
                    try{
                        comando = new SeleccionarJugador(subscriber,temelegido);
                        invoker.execute(comando);
                    }
                    catch(Exception e){
                        System.out.println("Debe escoger entre las opciones anteriores");
                        e.printStackTrace();

                    }
                    break;
                case "rendirse":
                    break;
                case "pasar":
                    break;
                case "salida mutua":
                    break;
                case "enviar mensaje":
                    break;
                case "recargar":
                    System.out.println("Escoja el numero del guerrero que desea enviar a atacar: ");
                    for(int i=0; i<guerreros.size();i++){
                        System.out.println(i+1+" "+guerreros.get(i).toString());
                    }
                    temelegido = scan.nextLine();
                    try{
                        comando = new RecargaAtaque(subscriber,temelegido);
                        invoker.execute(comando);
                    }
                    catch(Exception e){
                        System.out.println("Debe escoger entre las opciones anteriores");
                        e.printStackTrace();

                    }
                    
                    subscriber.getClient().guerreros.get(0).getAtaques().get(0).getEstado();
                    break;
                    
                default:
                    System.out.println("opción invalida");
            }
        }}

       
    }
    
    
    

