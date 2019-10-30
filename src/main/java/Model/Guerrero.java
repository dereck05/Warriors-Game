/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author derec
 */
public class Guerrero extends Personaje {
    //Orden de los daños: Fuego-Aire-Agua-Magia blanca-Magia negra-Electricidad-Hielo-Acid-Espiritualidad-Hierro
    private HashMap<Integer,ArrayList<Double>> damage;
    private Boolean activo;

    public Guerrero(String nom, String img, Double life, Double golpexTiempo, Double level, Double campo, Double nivelAparic, Double cost, Double x, Double y, ArrayList<Ataque> ataqu, String tipo) {
        super(nom, img, life, golpexTiempo, level, campo, nivelAparic, cost, x, y, ataqu, tipo);
        damage = new HashMap<>();
    }
    
    
 /*   public Guerrero(ArrayList<Ataque> a,HashMap<String,String> dam){
        this.Ataques = a;
        this.damage = dam;
        this.activo = false;        //hasta que lo selecciono se activa
        
    }*/

    public void setDamage(){
        for(int i=0; i<this.getAtaques().size();i++){
            ArrayList<Double> dañoTipo = new ArrayList<Double>();
            for(int j=0; j<10; j++){
                Double valorEntero = Math.floor(Math.random()*(100-20+1)+20);
                dañoTipo.add(valorEntero);
            }
            damage.put(i, dañoTipo);
        }
    }
    public void setActivo(Boolean act){
        this.activo = act;
    }

    public HashMap<Integer,ArrayList<Double>> getDamage(){
        return this.damage ;
    }
    public Boolean getActivo(){
        return this.activo;
    }


//    public void rebajarVida(int cant){
//        this.vida = this.vida - cant;
//    }
    public void recargarArmas(){
        
    }
}
