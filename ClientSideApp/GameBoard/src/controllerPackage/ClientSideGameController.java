/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import gameboard.GameBoardController;
import gameboard.GameBoardUtility;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mohamed Rashed
 */
public class ClientSideGameController {
    
    public static ClientSideGameController ref;
    private final GameBoardController currentGame;
    private boolean isMultiplayer;
    private boolean yourTurn;
    private int playerNumber;
    private static ArrayList<Integer> gameMoves = new ArrayList<Integer>();
    final char[] playerSymbole={'x','o'};
    private int winnerNumber=-1;

    public ClientSideGameController(boolean isMultiplayer, int plNumber,GameBoardController currGame) {
        this.isMultiplayer = isMultiplayer;
        this.playerNumber = plNumber;
        if(playerNumber==0)
            yourTurn=true;
        else
            yourTurn=false;
        this.currentGame=currGame;
        GameBoardUtility.resetAllBoxes();
        ref=this;
    }
    
    public boolean isIsMultiplayer() {
        return isMultiplayer;
    }

    public void setIsMultiplayer(boolean isMultiplayer) {
        this.isMultiplayer = isMultiplayer;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    
    
    
    public void makeAMove(int boxID){
        if(yourTurn && chkValidChosen(boxID)){
            admitMove(boxID,playerNumber);
            yourTurn=false; //disableAllButtons
            updateGameMovesArray(boxID);
            if(isMultiplayer){
                //sendToServer(boxID);
                System.out.println("Multi player game move need to be send" + boxID);
            }
            else{
                if(!checkEndOfGame()){
                    makeOpponentMove(generateRandomMove());
                }
                else{
                    System.out.println("Game Ended" + winnerNumber);
                }
            }
        }
    }
    
    boolean chkValidChosen(int boxID){
        if(gameMoves.contains(boxID)){
            return false;
        }
        else
            return true;
    }
    
    void updateGameMovesArray(int boxID){
        //updateArray of moves
        if(gameMoves.size()<9)
            gameMoves.add(boxID);
    }
    
    public void admitMove(int boxID,int plrNumber ){
        GameBoardUtility.setBox(boxID,""+playerSymbole[plrNumber]);
    }
    
    private boolean checkEndOfGame(){
        //winner 0=> x is winner
        //winner 1=> o is winner
        //winner 2=> Tie
        String[] line = currentGame.checkIfGameOver();
        for (String msg:line) {
            if(msg.equals("XXX")){
                winnerNumber=0;
                return true;
            }
            else if(msg.equals("OOO")){
                winnerNumber=1;
                return true;
            }
            else{
                if(gameMoves.size()<9){
                    return false;
                }
                winnerNumber=2;
                return true;
            }
        }
        return false;
    } 
    
    public void makeOpponentMove(int boxID){
        admitMove(boxID,1-playerNumber);
        updateGameMovesArray(boxID);
        yourTurn=true; //EnableFreeButtons
    }
    
    static int generateRandomMove() {
        int start=0,end = 8;
        Random rand = new Random();
        int random = rand.nextInt(end-start) + start;
        while(true) {
            
            if(!gameMoves.contains(random))
                break;
            else
                random = rand.nextInt(end-start) + start;
        }
        return random;
    } 
}
