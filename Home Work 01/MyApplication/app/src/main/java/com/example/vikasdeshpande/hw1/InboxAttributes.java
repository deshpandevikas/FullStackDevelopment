package com.example.vikasdeshpande.hw1;

import java.sql.Time;
import java.util.Date;

/**
 * Created by Vikas Deshpande on 9/17/2017.
 */

public class InboxAttributes
{
    int ID;
    String senderUName;
    String senderFullName;
    String dateSent;
    String timeSent;
    String msgSummary;


    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    String fullMessage;
    String region;
    boolean locked;
    boolean isRead;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getSenderUName() {
        return senderUName;
    }

    public void setSenderUName(String senderUName) {
        this.senderUName = senderUName;
    }

    public String getSenderFullName() {
        return senderFullName;
    }

    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public String getMsgSummary() {
        return msgSummary;
    }

    public void setMsgSummary(String msgSummary) {
        this.msgSummary = msgSummary;
    }



}
