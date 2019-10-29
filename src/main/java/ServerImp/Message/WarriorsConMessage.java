/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import Message.AMessage;
import Message.JsonManagement.InterfaceAdapter;


public class WarriorsConMessage extends AMessage{
    private boolean acceptedConnection;
    private String id;
    private String connMessage;
    
    public WarriorsConMessage(boolean accepted){
        super("Connection");
        this.acceptedConnection = accepted;
    }

    public boolean isAcceptedConnection() {
        return acceptedConnection;
    }

    public void setAcceptedConnection(boolean acceptedConnection) {
        this.acceptedConnection = acceptedConnection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConnMessage() {
        return connMessage;
    }

    public void setConnMessage(String connMessage) {
        this.connMessage = connMessage;
    }

}
