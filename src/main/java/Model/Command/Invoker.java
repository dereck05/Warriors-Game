/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Command;

import Model.Proxylog;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author derec
 */
public class Invoker {
    public void execute(ICommand command){      
        CommandManager cm = CommandManager.getInstance();
        cm.registerCommand(command);
        ICommand c=cm.getCommand();
        c.execute();
    }
    
}
