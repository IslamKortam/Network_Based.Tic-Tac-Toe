/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverdao;

import java.sql.Date;

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
    private Date lastVisit;
    private Boolean visible;

   

    public PlayerPojo(String userName, String nickName, String email, String password, int picture, int score, Date lastVisit,Boolean visible) {
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

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

   

}
