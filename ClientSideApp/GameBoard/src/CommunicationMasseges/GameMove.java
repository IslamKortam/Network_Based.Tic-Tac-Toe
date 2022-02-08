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
public class GameMove {
    private int boxID;

    public GameMove(int boxID) {
        this.boxID = boxID;
    }
    
    public GameMove() {
        this.boxID = -1;
    }

    public int getBoxID() {
        return boxID;
    }

    public void setBoxID(int boxID) {
        this.boxID = boxID;
    }
    
    
}
