/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Client;

import Model.Ataque;
import ServerImp.ContentServer.WarriorsContentServer;

/**
 *
 * @author derec
 */
public class WarriorsGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
           
            WarriorsContentServer wcs = new WarriorsContentServer();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        // TODO code application logic here
    }
    
}
