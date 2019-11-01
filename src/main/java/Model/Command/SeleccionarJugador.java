/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Client.SubscriberClient;
import Model.Guerrero;
import ServerImp.Publisher.WarriorsPublisher;
import ServerImp.Subscriber.WarriorsSubscriber;
import java.util.ArrayList;

/**
 *
 * @author maryp
 */
public class SeleccionarJugador implements ICommand{
    private String id;
    private WarriorsSubscriber jugador;
    
    public SeleccionarJugador(WarriorsSubscriber subs,String ide){
        this.jugador=subs;
        this.id = ide;
     
    }

    @Override
    public void execute() {
        Guerrero g = jugador.getClient().getGuerreros().get(Integer.parseInt(this.id)-1);
        System.out.println("\nInformacion General de "+g.getNombre()+"\n\n");
        System.out.println("Tipo: "+g.getTipo()+"\nVida: "+g.getVida()+"\nActivo: "+g.getActivo()+"\n\n------------------------Damage------------------------\n\t");
        ArrayList<Double> damages = g.getDamage().get(Integer.parseInt(this.id)-1);
        int cont = 0;
        for(Double d : damages){
            if (cont ==0)System.out.println("Fuego: "+d);
            if (cont ==1)System.out.println("\nAire: "+d);
            if (cont ==2)System.out.println("\nAgua: "+d);
            if (cont ==3)System.out.println("\nMagia Blanca: "+d);
            if (cont ==4)System.out.println("\nMagia Negra: "+d);
            if (cont ==5)System.out.println("\nElectricidad: "+d);
            if (cont ==6)System.out.println("\nHielo: "+d);
            if (cont ==7)System.out.println("\nAcid: "+d);
            if (cont ==8)System.out.println("\nEspiritualidad: "+d);
            if (cont ==9)System.out.println("\nHierro: "+d);
            cont++;



        }
    }
    
    
}
