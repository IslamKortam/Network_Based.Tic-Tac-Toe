/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationMasseges;

/**
 *
 * @author Salma
 */
public class Chatting {
    int receiver;
    int senderID;
    String message;

    public Chatting(int receiver, int senderID, String message) {
        this.receiver = receiver;
        this.senderID = senderID;
        this.message = message;
    }
    
}
