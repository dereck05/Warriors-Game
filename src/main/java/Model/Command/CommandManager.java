/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import java.util.ArrayList;

/**
 *
 * @author derec
 */
public class CommandManager {
    private static CommandManager instance = null;
    private static ArrayList<ICommand> command = new ArrayList<ICommand>();

    public static CommandManager getInstance(){
        if(instance != null)
            return instance;
        return new CommandManager();
    }
    public ICommand getCommand() {
        ICommand c = command.get(0);
        command.remove(0);
        return c;
    }

    public void registerCommand(ICommand command) {
        this.command.add(command);
    }
    
     
    
}
