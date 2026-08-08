package com.example;

public class Message {
    private String MsgType;
    private String Sender;
    private String Content;

    public String getMsgType() {
        return MsgType;
    }

    public String getUser () {
        return Sender;
    }

    public String getContent () {
        return Content;
    }

    public void setSender(String newSender) {
        this.Sender = newSender;
    }

    public void setContent(String newContent) {
        this.Content = newContent;
    }

    public void setMessageType(String newMsgType) {
        this.MsgType = newMsgType;
    }
}
