/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import ServerImp.Publisher.WarriorsPublisher;

/**
 *
 * @author derec
 */
public class Ataque implements ICommand {
    private WarriorsPublisher juego;
    public Ataque(WarriorsPublisher pJuego){
        this.juego=pJuego;
    }
    @Override
    public void execute(){};
    
}
