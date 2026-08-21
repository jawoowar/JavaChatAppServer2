package com.example;

/**
 *
 * Used as a middle man by {@link MessageHandler} when handling files to avoid repetative use of paramiters
 *
 * @author Jennifer
 *
 */

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
