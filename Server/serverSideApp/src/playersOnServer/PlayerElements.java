/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package playersOnServer;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Bahaa eldin
 */

public class PlayerElements {
    
    private Image img;
    private ImageView imgView;
    private Label usrName;
    private Label score;
    private Label status;
//    private Button inviteBtn;  
    private int userId;


    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public Label getUsrName() {
        return usrName;
    }

    public void setUsrName(Label usrName) {
        this.usrName = usrName;
    }

    public Label getScore() {
        return score;
    }

    public void setScore(Label score) {
        this.score = score;
    }

    public Label getStatus() {
        return status;
    }

    public void setStatus(Label status) {
        this.status = status;
    }

//    public Button getInviteBtn() {
//        return inviteBtn;
//    }
//
//    public void setInviteBtn(Button inviteBtn) {
//        this.inviteBtn = inviteBtn;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
