/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import CommunicationMasseges.CommunicationMassege;
import CommunicationMasseges.CommunicationMassegeType;
import CommunicationMasseges.GameStatusUpdate;
import CommunicationMasseges.StartMultiPlayerGame;
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
    private static int indexWinInArrayLine = -1;
    private int opponentID = 0;

    public int getOpponentID() {
        return opponentID;
    }

    public void setOpponentID(int opponentID) {
        this.opponentID = opponentID;
    }

    public static boolean isIsHardGame() {
        return isHardGame;
    }

    public ClientSideGameController(boolean isMultiplayer, int plNumber, int opponentID) {
        this.isMultiplayer = isMultiplayer;
        this.playerNumber = plNumber;
        this.opponentID = opponentID;
        Player.getThisPlayer().setStatus(isMultiplayer ? PlayerStatus.IN_MULTIPLAYER_GAME : PlayerStatus.IN_SINGLE_PLAYER_GAME);
        if (playerNumber == 0) {
            yourTurn = true;
        } else {
            yourTurn = false;
        }
        //GameBoardUtility.resetScene();
        GameBoardUtility.changeImgPlayerTurn(yourTurn);
        GameBoardUtility.showBtnSave(isMultiplayer);
        ref = this;
        if (isMultiplayer) {
            GameBoardUtility.setChatButtonDisable(true);
            Player player0, player1;
            if (yourTurn) {
                player0 = Player.getThisPlayer();
                player1 = Player.getPlayerByID(opponentID);
            } else {
                player0 = Player.getPlayerByID(opponentID);
                player1 = Player.getThisPlayer();
            }
            GameBoardUtility.setPlyer(player0.getFullName(), player0.getScore() + "", player0.getIconIndex(), player1.getFullName(), player1.getScore() + "", player1.getIconIndex());
        } else {  //Signle player
            GameBoardUtility.setChatButtonDisable(false);
            Player player0 = Player.getThisPlayer();
            GameBoardUtility.setPlyer(player0.getFullName(), player0.getScore() + "", player0.getIconIndex(), (isHardGame ? "Hard" : "Easy") + " Robot", (isHardGame ? "500" : "100"), 100);
        }
    }

    public static void loadGame(StartMultiPlayerGame order) {
        ClientSideGameController loadedGame = new ClientSideGameController(true, order.getTurn(), order.getOponentID());
        for (int move : order.getArrayOfMoves()) {
            if (loadedGame.yourTurn) {
                loadedGame.admitMove(move, loadedGame.playerNumber);
                loadedGame.yourTurn = false; //disableAllButtons
                loadedGame.updateGameMovesArray(move);

            } else {
                loadedGame.admitMove(move, 1 - loadedGame.playerNumber);
                loadedGame.updateGameMovesArray(move);
                loadedGame.yourTurn = true; //EnableFreeButtons
            }
            GameBoardUtility.changeImgPlayerTurn(loadedGame.yourTurn);
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
            Boolean gameEnded = checkEndOfGame();
            if (isMultiplayer) {
                //sendToServer(boxID);
                MainController.getRef().sendMoveToServer(boxID);
            } else {
                if (!gameEnded) {
                    if (isHardGame) {
                        makeOpponentMove(generateBestMove());
                    } else {
                        makeOpponentMove(generateRandomMove(gameMoves));
                    }
                } else {

                    if (winnerNumber == 0) {

                        declareWinner();

                    } else if (winnerNumber == 1) {

                        declareLooser();
                    } else {

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
        int counter = 0;
        for (String msg : line) {
            if (msg.equals(new String(new char[3]).replace("\0", "" + playerSymbole[playerNumber]))) {
                winnerNumber = 0;
                result = true;
                indexWinInArrayLine = counter;
            } else if (msg.equals(new String(new char[3]).replace("\0", "" + playerSymbole[1 - playerNumber]))) {
                winnerNumber = 1;
                result = true;
                indexWinInArrayLine = counter;
            }
            counter++;
        }
        if (gameMoves.size() == 9 && result == false) {
            winnerNumber = 2;
            result = true;
        }
        if (result == true) {
            if (winnerNumber == 0) {
                GameBoardUtility.colorButtonWhenEndGame(indexWinInArrayLine, (GameStatusUpdate.GameStatus.WINNER));
            } else if (winnerNumber == 1) {
                GameBoardUtility.colorButtonWhenEndGame(indexWinInArrayLine, (GameStatusUpdate.GameStatus.LOSER));
            }

            if (!isMultiplayer) {
                GameStatusUpdate update = new GameStatusUpdate(GameStatusUpdate.GameStatus.TIE);
                switch (winnerNumber) {
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
        }
        return result;
    }

    public void makeOpponentMove(int boxID) {
        admitMove(boxID, 1 - playerNumber);
        updateGameMovesArray(boxID);
        yourTurn = true; //EnableFreeButtons

        if (checkEndOfGame() && !isIsMultiplayer()) {
            if (winnerNumber == 1) {
                declareLooser();
            } else {  //Tie
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
        gameboard.GameBoardController.getRef().declareGameStatusChange("Winner");
        ref = null;

    }

    public void declareLooser() {
        gameboard.GameBoardController.getRef().declareGameStatusChange("Loser");
        ref = null;
    }

    public void declareTie() {
        gameboard.GameBoardController.getRef().declareGameStatusChange("Tie");
        ref = null;
    }

    public void gameSaved() {
        gameboard.GameBoardController.getRef().declareGameStatusChange("Game Saved Successfuly");
        ref = null;
    }

    public void otherUserDisconnected() {
        String msg = "Your oppponent (" + Player.getPlayerByID(opponentID).getUserName() + ") Disconnecteed! Your game is Saved.\nYou may replay it later...";
        gameboard.GameBoardController.getRef().declareGameStatusChange(msg);
        ref = null;
    }
    
    public void otherUserAborted(){
        String msg = "Your oppponent (" + Player.getPlayerByID(opponentID).getUserName() + ") Aborted the Game.\nYou Win...";
        gameboard.GameBoardController.getRef().declareGameStatusChange(msg);
        ref = null;
    }

    public void createAlertSaveGame() {
        gameboard.GameBoardController.getRef().saveGameRequest();
        ref = null;
    }

    public void abortGame() {
        if (this.isMultiplayer) {
            GameStatusUpdate update = new GameStatusUpdate(GameStatusUpdate.GameStatus.Aborted);
            String s = ParserPackage.Parser.gson.toJson(update);
            CommunicationMassege comMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, s);
            CommHandlerPK.ClientConnectionHandler.ref.sendCommMsgToServer(comMsg);
            gameboard.GameBoardController.getRef().declareGameStatusChange("You aborted the Game, You Lost!");
            ref = null;
        } else {
            GameStatusUpdate update = new GameStatusUpdate(GameStatusUpdate.GameStatus.LOSER);
            String s = ParserPackage.Parser.gson.toJson(update);
            CommunicationMassege comMsg = new CommunicationMassege(CommunicationMassegeType.GAME_STATUS, s);
            CommHandlerPK.ClientConnectionHandler.ref.sendCommMsgToServer(comMsg);
            gameboard.GameBoardController.getRef().declareGameStatusChange("You aborted the Game, You Lost!");
            ref = null;
        }
    }
}
