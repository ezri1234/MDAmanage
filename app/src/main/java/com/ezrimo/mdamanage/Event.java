package com.ezrimo.mdamanage;

import java.util.Date;

public class Event {
    private Date date;
    private String uid;

    public Event(Date date, String uid){
        this.setDate(date);
        this.setUid(uid);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
