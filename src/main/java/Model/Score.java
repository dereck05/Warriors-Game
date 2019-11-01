/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author derec
 */
public class Score {
    private int ataqueExitoso;
    private int ataqueFracasado;
    private int muertes;
    private int ganes;
    private int perdidas;
    private int rendiciones;
    private String id;
    
    public Score(){
        this.ataqueExitoso = 0;
        this.ataqueFracasado = 0;
        this.ganes = 0;
        this.muertes = 0;
        this.perdidas = 0;
        this.rendiciones = 0;
    }
    public void addAtaqueExitoso(){
        this.ataqueExitoso = this.ataqueExitoso+1;
    }
    public void addAtaqueFracasado(){
        this.ataqueFracasado = this.ataqueFracasado+1;
    }
    public void addMuerte(){
        this.muertes= this.muertes+1;
    }
    public void addGane(){
        this.ganes = this.ganes+1;
    }
    public void addPerdida(){
        this.perdidas = this.perdidas+1;
    }
    public void addRendicion(){
        this.rendiciones = this.rendiciones+1;
    }
    public int getAtaquesExitosos(){
        return this.ataqueExitoso;
    }
    public int getAtaquesFracasados(){
        return this.ataqueFracasado;
    }
    public int getMuertes(){
        return this.muertes;
    }
    public int getGanes(){
        return this.ganes;
    }
    public int getPerdidas(){
        return this.perdidas;
    }
    public int getRendiciones(){
        return this.rendiciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
