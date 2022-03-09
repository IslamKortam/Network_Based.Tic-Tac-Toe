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
public class GameStatusUpdate {
  
    
    public enum GameStatus{
        WINNER,
        LOSER,
        TIE,
        GameSaved,
        OtherPlayerDisconnected,
        Aborted
    }
      
      
    private GameStatus status;
    public GameStatusUpdate(GameStatus status) {
        this.status = status;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
    
    
    
}
