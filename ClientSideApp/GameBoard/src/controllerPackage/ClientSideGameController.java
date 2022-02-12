/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.GameStatusUpdate;
import controllerPackage.BestMove.Move;
import gameboard.GameBoardUtility;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Mohamed Rashed
 */
public class ClientSideGameController {
    
    private static ClientSideGameController ref;
    private boolean isMultiplayer;
    private boolean yourTurn;
    private int playerNumber;
    private ArrayList<Integer> gameMoves = new ArrayList<Integer>();
    final char[] playerSymbole = {'X', 'O'};
    private int winnerNumber = -1;
    private String[] board = {"a", "a", "a", "a", "a", "a", "a", "a", "a"};
    private char xoBoard[][] = new char[3][3];
    private static boolean isHardGame = false;

    public static boolean isIsHardGame() {
        return isHardGame;
    }
    
    public ClientSideGameController(boolean isMultiplayer, int plNumber, int opponentID) {
        this.isMultiplayer = isMultiplayer;
        this.playerNumber = plNumber;
        Player.getThisPlayer().setStatus(isMultiplayer?PlayerStatus.IN_MULTIPLAYER_GAME : PlayerStatus.IN_SINGLE_PLAYER_GAME);
        if (playerNumber == 0) {
            yourTurn = true;
        } else {
            yourTurn = false;
        }
        GameBoardUtility.resetAllBoxes();
        GameBoardUtility.changeImgPlayerTurn(yourTurn);
        GameBoardUtility.showBtnSave(isMultiplayer);
        ref = this;
        if(isMultiplayer){
            Player player0, player1;
            if(yourTurn){
                player0 = Player.getThisPlayer();
                player1 = Player.getPlayerByID(opponentID);
            }else{
                player0 = Player.getPlayerByID(opponentID);
                player1 = Player.getThisPlayer();
            }
            GameBoardUtility.setPlyer(player0.getFullName(), player0.getScore() + "", player0.getIconIndex(), player1.getFullName(), player1.getScore() + "", player1.getIconIndex());
        }else{  //Signle player
            Player player0 = Player.getThisPlayer();
            GameBoardUtility.setPlyer(player0.getFullName(), player0.getScore() + "", player0.getIconIndex(), (isHardGame? "Hard" : "Easy") + " Robot", (isHardGame? "500" : "100"), 100);
        }
    }
    
    public static ClientSideGameController getRef() {
        return ref;
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
    
    public static void setIsHardGame(boolean isHardGame) {
        ClientSideGameController.isHardGame = isHardGame;
    }
    
    public void makeAMove(int boxID) {
        if (yourTurn && chkValidChosen(boxID)) {
            admitMove(boxID, playerNumber);
            yourTurn = false; //disableAllButtons
            updateGameMovesArray(boxID);
            GameBoardUtility.changeImgPlayerTurn(yourTurn);
            if (isMultiplayer) {
                //sendToServer(boxID);
                MainController.getRef().sendMoveToServer(boxID);
                System.out.println("Multi player game move need to be send" + boxID);
            } else {
                if (!checkEndOfGame()) {
                    if (isHardGame) {
                        makeOpponentMove(generateBestMove());
                    } else {
                        makeOpponentMove(generateRandomMove(gameMoves));
                    }
                } else {
                    
                    if (winnerNumber == 0) {
                        System.out.println("Winner");
                        
                        declareWinner();
                        
                    } else if (winnerNumber == 1) {
                        System.out.println("Looser");
                        
                        declareLooser();
                    } else {
                        System.out.println("Tie");
                        
                        //Tie
                        declareTie();
                    }
                    
                    Player.getThisPlayer().setStatus(PlayerStatus.ONLINE);
                }
            }
        }
    }
    
    boolean chkValidChosen(int boxID) {
        if (gameMoves.contains(boxID)) {
            return false;
        } else {
            return true;
        }
    }
    
    void updateGameMovesArray(int boxID) {
        //updateArray of moves
        if (gameMoves.size() < 9) {
            gameMoves.add(boxID);
        }
    }
    
    public void admitMove(int boxID, int plrNumber) {
        GameBoardUtility.setBox(boxID, "" + playerSymbole[plrNumber]);
        board[boxID] = playerSymbole[plrNumber] + "";
    }
    
    private boolean checkEndOfGame() {
        //winner 0=> x is winner
        //winner 1=> o is winner
        //winner 2=> Tie
        Boolean result = false;
        String[] line = checkIfGameOver();
        for (String msg : line) {
            if (msg.equals("XXX")) {
                winnerNumber = 0;
                result =  true;
            } else if (msg.equals("OOO")) {
                winnerNumber = 1;
                result = true;
            }
        }
        if (gameMoves.size() == 9 && result == false) {
            winnerNumber = 2;
            result =  true;
        }
        if(result == true){
            GameStatusUpdate update = new GameStatusUpdate(GameStatusUpdate.GameStatus.TIE);
            switch(winnerNumber){
                case 0:
                    //Winner
                    update.setStatus(GameStatusUpdate.GameStatus.WINNER);
                    break;
                case 1:
                    //Loser
                    update.setStatus(GameStatusUpdate.GameStatus.LOSER);
                    break;
                case 2:
                    //Tie
                    update.setStatus(GameStatusUpdate.GameStatus.TIE);
                    break;
            }
            String s = ParserPackage.Parser.gson.toJson(update);
            CommunicationMassege comMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, s);
            CommHandlerPK.ClientConnectionHandler.ref.sendCommMsgToServer(comMsg);
        }
        return result;
    }
    
    public void makeOpponentMove(int boxID) {
        admitMove(boxID, 1 - playerNumber);
        updateGameMovesArray(boxID);
        yourTurn = true; //EnableFreeButtons

        if (checkEndOfGame() && !isIsMultiplayer()) {
            if (winnerNumber == 1) {
                System.out.println("Looser");
                declareLooser();
            }else{  //Tie
                declareTie();
            }
        }
        GameBoardUtility.changeImgPlayerTurn(yourTurn);
    }
    
    static int generateRandomMove(ArrayList<Integer> gameMoves) {
        int start = 0, end = 8;
        Random rand = new Random();
        int random = rand.nextInt(end - start) + start;
        while (true) {
            
            if (!gameMoves.contains(random)) {
                break;
            } else {
                random = rand.nextInt(end - start) + start;
            }
        }
        return random;
    }
    
    String[] line = new String[8];
    
    public String[] checkIfGameOver() {
        for (int i = 0; i < 8; i++) {
            switch (i) {
                //horizontal cases
                case 0:
                    line[i] = board[0] + board[1] + board[2];
                    break;
                case 1:
                    line[i] = board[3] + board[4] + board[5];
                    break;
                case 2:
                    line[i] = board[6] + board[7] + board[8];
                    break;
                //vertical cases
                case 3:
                    line[i] = board[0] + board[3] + board[6];
                    break;
                case 4:
                    line[i] = board[1] + board[4] + board[7];
                    break;
                case 5:
                    line[i] = board[2] + board[5] + board[8];
                    break;
                //Diagonal cases
                case 6:
                    line[i] = board[0] + board[4] + board[8];
                    break;
                case 7:
                    line[i] = board[2] + board[4] + board[6];
                    break;
            }
        }
        return line;
    }
    
    private void fillXOBoardArray(String[] myBoard) {
        int currentIndex = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                xoBoard[i][j] = myBoard[currentIndex].charAt(0);
                currentIndex++;
            }
        }
    }
    
    private int generateBestMove() {
        fillXOBoardArray(board);
        Move bestMove = BestMove.findBestMove(xoBoard);
        if (bestMove.row == 0 && bestMove.col == 0) {
            return 0;
        } else if (bestMove.row == 0 && bestMove.col == 1) {
            return 1;
        } else if (bestMove.row == 0 && bestMove.col == 2) {
            return 2;
        } else if (bestMove.row == 1 && bestMove.col == 0) {
            return 3;
        } else if (bestMove.row == 1 && bestMove.col == 1) {
            return 4;
        } else if (bestMove.row == 1 && bestMove.col == 2) {
            return 5;
        } else if (bestMove.row == 2 && bestMove.col == 0) {
            return 6;
        } else if (bestMove.row == 2 && bestMove.col == 1) {
            return 7;
        } else if (bestMove.row == 2 && bestMove.col == 2) {
            return 8;
        } else {
            return -1;
        }
    }
    
    public void declareWinner() {
        gameboard.GameBoardController.getRef().declareEndOfGame("Winner");
        ref = null;
        
    }
    
    public void declareLooser() {
        gameboard.GameBoardController.getRef().declareEndOfGame("Loser");
        ref = null;
    }
    
    public void declareTie() {
        gameboard.GameBoardController.getRef().declareEndOfGame("Tie");
        ref = null;
    }
}
