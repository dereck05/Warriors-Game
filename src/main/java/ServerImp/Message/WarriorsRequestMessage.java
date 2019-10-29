/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerImp.Message;

import Message.AMessage;


public class WarriorsRequestMessage extends AMessage{
    private int RequestId;
    private String RequestString;
    
    public WarriorsRequestMessage(){
        super("Request");
    }

    public int getRequestId() {
        return RequestId;
    }

    public void setRequestId(int RequestId) {
        this.RequestId = RequestId;
    }

    public String getRequestString() {
        return RequestString;
    }

    public void setRequestString(String RequestString) {
        this.RequestString = RequestString;
    }
    
    
}
