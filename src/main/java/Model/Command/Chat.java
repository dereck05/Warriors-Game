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
public class Chat implements ICommand {
    WarriorsPublisher juego;

    public Chat(WarriorsPublisher juego) {
        this.juego = juego;
    }
    
    @Override
    public void execute(){};
    
}
