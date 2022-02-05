/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author imkor
 */
public class CommunicationMassege {
    private CommunicationMassegeType type;
    private String msgBody;

    public CommunicationMassege(CommunicationMassegeType type, String msgBody) {
        this.type = type;
        this.msgBody = msgBody;
    }

    public void setType(CommunicationMassegeType type) {
        this.type = type;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public CommunicationMassegeType getType() {
        return type;
    }

    public String getMsgBody() {
        return msgBody;
    }
    
    
    
}
