/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Ohjelmistokehitys
 */
package message;

import java.io.Serializable;
import javafx.scene.paint.Color;

/*this class can be used as serializable object between the client and 
 server in chat program
 */
public class ChatMessage implements Serializable {

    private String userName;
    private String chatMessage;
    //private String messageColor;
    private double colorBlue;
    private double colorRed;
    private double colorGreen;
    private int fontSize;   // min 12 max 25
    private boolean isPrivate;
    private String privateName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }

    public Color getMessageColor() {
        //Color messageColor;
        return Color.color(colorRed, colorGreen, colorBlue);
    }

    public void setMessageColor(Color messageColor) {
        this.colorRed = messageColor.getRed();
        this.colorGreen = messageColor.getGreen();
        this.colorBlue = messageColor.getBlue();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        System.out.println("setFontSize");
        if ((fontSize > 11) && (fontSize < 26)) {
            System.out.println("täällä");
            this.fontSize = fontSize;
        } else {
            this.fontSize = 12;
        }
    }

    public boolean isIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

}
