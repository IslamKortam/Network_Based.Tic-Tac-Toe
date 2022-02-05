/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllerPackage;

import serverdao.PlayerPojo;

/**
 *
 * @author imkor
 */
public class Player {
    
    /* Member Data */
    private int id;
    private String userName;
    private String fullName;
    private String email;
    private int score;
    private int iconIndex;
    private PlayerStatus status;
    
    
    /* Constructors */
    public Player(int id, String userName, String fullName, String email, int score, int iconIndex, PlayerStatus status) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
        this.email = email;
        this.score = score;
        this.iconIndex = iconIndex;
        this.status = status;
    }
    
    public Player() {
        this.id = 0;
        this.userName = "";
        this.fullName = "";
        this.email = "";
        this.score = 0;
        this.iconIndex = 0;
        this.status = PlayerStatus.OFFLINE;
    }
    
    public Player(PlayerPojo playerPojo){
        this.id = playerPojo.getID();
        this.userName = playerPojo.getUserName();
        this.fullName = playerPojo.getNickName();
        this.email = playerPojo.getEmail();
        this.score = playerPojo.getScore();
        this.iconIndex = playerPojo.getPicture();
        this.status = PlayerStatus.OFFLINE;
    }

    /* Setters */
    public void setId(int id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setIconIndex(int iconIndex) {
        this.iconIndex = iconIndex;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    
    /* Getters */
    
    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public int getScore() {
        return score;
    }

    public int getIconIndex() {
        return iconIndex;
    }

    public PlayerStatus getStatus() {
        return status;
    }
    
    
}
