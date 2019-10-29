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
public class Guerrero {
    private ArrayList<Ataque> Ataques;
    private HashMap<String,String> damage;
    private Boolean activo;
    
    public Guerrero(ArrayList<Ataque> a,HashMap<String,String> dam){
        this.Ataques = a;
        this.damage = dam;
        this.activo = false;        //hasta que lo selecciono se activa
        
    }
    public void setAtaque(ArrayList<Ataque> a){
        this.Ataques = a;
    }
    public void setDamage(HashMap<String,String> dam){
        this.damage = dam;
    }
    public void setActivo(Boolean act){
        this.activo = act;
    }
    public ArrayList<Ataque> getAtaque(){
        return this.Ataques;
    }
    public HashMap<String,String> getDamage(){
        return this.damage ;
    }
    public Boolean getActivo(){
        return this.activo;
    }
    public void addAtaque(Ataque a){
        this.Ataques.add(a);
    }
    public void addDamage(String nombre,String damage){
        this.damage.replace(nombre, damage);
    }
    public void rebajarVida(int cant){
        this.vida = this.vida - cant;
    }
    public void recargarArmas(){
        
    }
}
