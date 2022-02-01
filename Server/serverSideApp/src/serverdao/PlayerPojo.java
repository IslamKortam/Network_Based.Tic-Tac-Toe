/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

/**
 *
 * @author Salma
 */
public class PlayerPojo {

    private String userName;
    private String nickName;
    private String email;
    private String password;
    private int picture;
    private int score;
    private String lastVisit;
    private int visible;

   

    public PlayerPojo(String userName, String nickName, String email, String password, int picture, int score, String lastVisit,int visible) {
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.picture = picture;
        this.score = score;
        this.lastVisit = lastVisit;
        this.visible= visible;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(String lastVisit) {
        this.lastVisit = lastVisit;
    }

   
     public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }

}
