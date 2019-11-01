/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;

import Model.Ataque;
import Model.Command.AtaqueCommand;
import Model.Command.Chat;
import Model.Command.Comodin;
import Model.Command.ICommand;
import Model.Command.Invoker;
import Model.Command.RecargaAtaque;
import Model.Command.Rendirse;
import Model.Command.SalidaMutua;
import Model.Command.SeleccionarJugador;
import Model.Command.PasoTurno;
import Model.Guerrero;
import Model.Personaje;
import Model.Score;
import Model.SuperFactory;
import ServerImp.Message.AtaqueMessage;
import ServerImp.Message.FeedMessage;
import ServerImp.Message.LogMessage;
import ServerImp.Message.RankingMessage;
import ServerImp.Message.ScoreMessage;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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
    public HashMap<String,String> mensajes;
    private Score score;
    private Boolean status;
    private  Scanner scan;
    private String topic;
    public boolean actual;
    public  Map<String,Integer> topics;
    public ArrayList<String>logs;
    private Score scoreOtroJugador;
    private static ArrayList<Score> ranking;

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
        this.mensajes=new HashMap<>();
        this.guerreros = new ArrayList<Guerrero>();
        this.status=true;
        this.logs = new ArrayList<String>();

    }
    public Jugador(String i, WarriorsPublisher pPublisher){
        this.id=i;
        this.score=new Score();
        this.guerreros = new ArrayList<Guerrero>();
        this.publisher= pPublisher;
        this.status = true;
    }
    public Jugador(String i){
        this.id = i;
        this.guerreros = new ArrayList<Guerrero>();
        this.score = new Score();
        this.status = true;
    }

    public ArrayList<Score> getRanking() {
        return ranking;
    }

    public void setRanking(ArrayList<Score> ranking) {
        this.ranking = ranking;
    }

    public Score getScoreOtroJugador() {
        return scoreOtroJugador;
    }

    public void setScoreOtroJugador(Score scoreOtroJugador) {
        this.scoreOtroJugador = scoreOtroJugador;
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
    public void setStatus(Boolean s){
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
    public Boolean getStatus(){
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
       this.score.setId(subscriber.getId());
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
        System.out.println("mensajes");
        System.out.println("recargar");
        System.out.println("ver mis personajes");
        System.out.println("comodin");
        System.out.println("obtener log");
        System.out.println("mi estatus");
        System.out.println("jugador estatus");
        System.out.println("ranking");
        System.out.println("======================================");

   }
   private void run() throws InterruptedException{
       Invoker invoker = new Invoker();
       ICommand comando;
       String temelegido;
       LocalDateTime locaDate = LocalDateTime.now();

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
                        for(int j=1; j<=2;j++){
                            System.out.println("Ingrese el nombre del arma: ");
                            String tempNombreArma = scan.nextLine();
                            System.out.println("Ingrese el path con la imagen del arma: ");
                            String tempImagenArma = scan.nextLine();
                            Ataque a=sf.crearAtaque(false, tempNombreArma, 0.0, 0.0, 0.0, 0.0,tempImagenArma , "activa");
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

                    }else{
                        subscriber.subscribe(topic);
                        System.out.println("Juego creado con éxito");
                    }
                    }
                    catch(Exception e){
                        System.out.println("No se pudo crear el juego");
                    }
                    actual=true;


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
                    actual=false;
                    break;
                case "atacar":
                    if(actual){
                    System.out.println("Escoja el numero del guerrero que desea enviar a atacar: ");
                    Guerrero guerrero = escogerGuerreros();
                    if(guerrero.equals(null)){
                        System.out.println("Ataque erroneo");
                    }
                    else{
                        int numArma = escogerArma(guerrero);
                       // ArrayList<Double> daño= escogerArma(guerrero);
                        if(numArma<0){
                            System.out.println("Ataque erroneo");
                        }else{
                            ArrayList<Double> daño = obtenerDañoArma(guerrero,numArma);
                            comando = new AtaqueCommand(subscriber,daño,topic,guerrero.getNombre(),guerrero.getAtaques().get(numArma).getNombre());
                            invoker.execute(comando);
                            System.out.println("El ataque ha sido exitoso");    }
                        actual=false;

                    }

                    }
                    else{
                        System.out.println("No es su turno");
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
                    try{
                        comando = new Rendirse(subscriber,this.topic);
                        invoker.execute(comando);
                        //System.exit(0);
                    }
                    catch(Exception e){
                        System.out.println("Debe escoger entre las opciones anteriores");
                        e.printStackTrace();

                    }
                    break;
                case "pasar":
                    if(actual){
                        comando = new PasoTurno(subscriber,topic);
                        invoker.execute(comando);
                        this.actual=false;
                        System.out.println("Ahora es turno del siguiente jugador");}
                    else{
                        System.out.println("No puede pasar porque no es su turno");
                    }
                    break;
                case "salida mutua":
                    try{
                        
                        comando = new SalidaMutua(subscriber,this.topic,"El otro jugador quiere hacer salida mutua, aceptas [y/n]:");
                        invoker.execute(comando);
                    }
                    catch(Exception e){
                        System.out.println("Debe escoger entre las opciones anteriores");
                        e.printStackTrace();

                    }


                    break;
                case "mensajes":
                    System.out.println("\t-------------MENSAJES-------------");
                    for(HashMap.Entry<String, String> entry : mensajes.entrySet()){
                        System.out.println("Jugador: "+entry.getKey()+", mensaje: "+entry.getValue());
                    }
                    System.out.println("Desea enviar un nuevo mensaje? y/n");
                    String res = scan.nextLine();
                    switch(res){
                        case "y":
                            System.out.println("Escriba el mensaje que desea enviar: ");
                            String mensaje = scan.nextLine();
                            comando = new Chat(subscriber,topic,"Tienes un nuevo mensaje",mensaje);
                            invoker.execute(comando);
                            System.out.println("Mensaje enviado con éxito");
                            break;
                        case "n":
                            break;
                        default:
                            System.out.println("Comando invalido");
                            break;
                    }


                    break;
                case "recargar":
                    System.out.println("Escoja el numero del guerrero que desea recargarle las armas: ");
                    for(int i=0; i<guerreros.size();i++){
                        
                        System.out.println(i+1+" "+guerreros.get(i).getNombre());
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
                case "ver mis personajes":

                    for(Guerrero gue: guerreros){
                         System.out.println("\t------------------------------------------");
                        String estado;
                        if(gue.getActivo()){
                            estado = "vivo";
                        }
                        else{
                            estado = "muerto";
                        }
                        System.out.println("Nombre: "+gue.getNombre()+", tipo: "+gue.getTipo()+", vida: "+gue.getVida()+", estado: "+estado);
                        System.out.println("\t------------------ARMAS-------------------");
                        for(Ataque ataque: gue.getAtaques()){
                            System.out.println("Nombre: "+ataque.getNombre()+", estado: "+ataque.getEstado());
                        }
                        System.out.println();
                    }
                    break;
                case "comodin":
                    if(actual){
                    LocalDateTime locaDate1 = LocalDateTime.now();
                    int minutes = (int) ChronoUnit.MINUTES.between(locaDate, locaDate1);
                    if(minutes>=1){
                        System.out.println("Escoja una opción:");
                        System.out.println("1. Usar dos personajes.");
                        System.out.println("2. Usar dos armas.");
                        String tempOpcion = scan.nextLine();
                        int arma1;
                        int arma2;
                        ArrayList<Double> daño1= new ArrayList<Double>();
                        ArrayList<Double> daño2= new ArrayList<Double>();
                        ArrayList<String> guerreros = new ArrayList<String>();
                        ArrayList<String> armas = new ArrayList<String>();
                        switch(tempOpcion){
                            case "1":
                                System.out.println("\t------------------PRIMER GUERRERO------------------");
                                Guerrero gue1 = escogerGuerreros();
                                arma1 =escogerArma(gue1);
                                daño1= obtenerDañoArma(gue1,arma1);
                                System.out.println("\t------------------SEGUNDO GUERRERO------------------");
                                Guerrero gue2 = escogerGuerreros();
                                arma2 = escogerArma(gue2);
                                daño2 =obtenerDañoArma(gue2,arma2);
                                guerreros.add(gue1.getNombre());
                                guerreros.add(gue2.getNombre());
                                armas.add(gue1.getAtaques().get(arma1).getNombre());
                                armas.add(gue2.getAtaques().get(arma2).getNombre());
                                break;
                            case "2":
                                System.out.println("\t------------------------ESCOGER GUERRERO-----------------------");
                                Guerrero guee = escogerGuerreros();
                                System.out.println("\t------------------------ESCOGER ARMAS-----------------------");
                                arma1 = escogerArma(guee);
                                daño1 =obtenerDañoArma(guee,arma1);
                                arma2 = escogerArma(guee);
                                daño2=obtenerDañoArma(guee,arma2);
                                guerreros.add(guee.getNombre());
                                armas.add(guee.getAtaques().get(arma1).getNombre());
                                armas.add(guee.getAtaques().get(arma2).getNombre());
                                break;
                        }

                        comando = new Comodin(this.subscriber,this.topic,daño1,daño2,guerreros,armas);
                        invoker.execute(comando);
                        System.out.println("Comodin utilizado con exito");
                        actual =false;
                        locaDate = LocalDateTime.now();
                    }
                    else{
                        System.out.println("Comodin no disponible");
                    }
                    }
                    else{
                        System.out.println("No es su turno");
                    }
                    break;
                case "obtener log":
                    LogMessage m =new LogMessage(topic,this.subscriber.getId());
                    this.subscriber.sendMessage(m);
                    Thread.sleep(3000);
                    System.out.println("\t----------------jugador estatus---------------------");
                    for (String log : logs){
                        System.out.println(log);
                    }
                    break;
                case "mi estatus":
                    System.out.println("Ganadas: "+this.score.getGanes());
                    System.out.println("Perdidas: "+this.score.getPerdidas());
                    System.out.println("Ataques exitosos: "+this.score.getAtaquesExitosos());
                    System.out.println("Ataques fracasados: "+this.score.getAtaquesFracasados());
                    System.out.println("Rendiciones: "+this.score.getRendiciones());
                    System.out.println("Muertes: "+this.score.getMuertes());
                    break;
                case "jugador estatus":
                    ScoreMessage m1 =new ScoreMessage(topic,this.subscriber.getId());

                    this.subscriber.sendMessage(m1);
                    Thread.sleep(5000);
                    System.out.println("Ganadas: "+this.scoreOtroJugador.getGanes());
                    System.out.println("Perdidas: "+this.scoreOtroJugador.getPerdidas());
                    System.out.println("Ataques exitosos: "+this.scoreOtroJugador.getAtaquesExitosos());
                    System.out.println("Ataques fracasados: "+this.scoreOtroJugador.getAtaquesFracasados());
                    System.out.println("Rendiciones: "+this.scoreOtroJugador.getRendiciones());
                    System.out.println("Muertes: "+this.scoreOtroJugador.getMuertes());
                    break;
                case "ranking":
                    RankingMessage m2 = new RankingMessage(topic);
                    this.subscriber.sendMessage(m2);
                    Thread.sleep(5000);
                    quick_srt(ranking, 0, ranking.size()-1);
                    System.out.println("\t----------RANKING DE JUGADORES----------------");
                    int cont=1;
                    for(int i = ranking.size()-1;i>=0;i--){
                        
                        System.out.println(cont+". Jugador: "+ranking.get(i).getId()+" ganes: "+ranking.get(i).getGanes());
                        cont++;
                    }
                    break;
                default:
                    System.out.println("opción invalida");
            }
        }}
     public static void quick_srt(ArrayList<Score> ranking,int low,int n){
      int lo = low;
      int hi = n;
      if (lo >= n) {
          return;
      }
      int mid = ranking.get((lo + hi) / 2).getGanes();
      while (lo < hi) {
          while (lo<hi && ranking.get(lo).getGanes() < mid) {
              lo++;
          }
          while (lo<hi && ranking.get(hi).getGanes() > mid) {
              hi--;
          }
          if (lo < hi) {
              Score T = ranking.get(lo);
              ranking.set(lo, ranking.get(hi));
              ranking.set(hi, T);
          }
      }
      if (hi < lo) {
          int T = hi;
          hi = lo;
          lo = T;
      }
      quick_srt(ranking, low, lo);
      quick_srt(ranking, lo == low ? lo+1 : lo, n);
   }
    public Guerrero escogerGuerreros(){
            Guerrero guerrero = null;
            for(int i=0; i<guerreros.size();i++){
                if(guerreros.get(i).getActivo()){
                System.out.println(i+" "+guerreros.get(i).getNombre());}
            }
            String temElegido = scan.nextLine();
            try{
                Integer elegido = Integer.parseInt(temElegido);
                guerrero = guerreros.get(elegido);
                return guerrero;
            }
            catch(Exception e){
                System.out.println("Error al realizar el ataque");
            }
            return guerrero;

    }

     public int escogerArma(Guerrero guerrero){
            int escogida = -1;
            System.out.println("Escoja el número de arma que desea usar");
            System.out.println(guerrero.getAtaques().size());
            for(int j=0; j<guerrero.getAtaques().size();j++){
                if(guerrero.getAtaques().get(j).getEstado().equals("activa")){
                System.out.println(j+" "+guerrero.getAtaques().get(j).getNombre());}
            }
            String temAElegida = scan.nextLine();
            try{
                Integer elegida = Integer.parseInt(temAElegida);
               // daño = guerrero.getDamage().get(elegida);
                guerrero.getAtaques().get(elegida).setEstado("inactiva");
                return elegida;
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println("Error al realizar el ataque");
            }
            return escogida;
     }
     public ArrayList<Double> obtenerDañoArma(Guerrero guerrero,int escogida){
         ArrayList<Double> daño = guerrero.getDamage().get(escogida);
         return daño;
     }
    }
