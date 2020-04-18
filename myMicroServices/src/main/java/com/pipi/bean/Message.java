package com.pipi.bean;

public class Message {
    private String myMessage;

    public Message(String myMessage) {
        this.myMessage = myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }

    /** getter should be define, for automatic conversion**/
    public String getMyMessage() {
        return myMessage;
    }
}