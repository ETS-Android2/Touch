package com.seeds.touch.Entity.Entities;


import java.io.Serializable;
import java.util.Calendar;

public class News implements Serializable{
    private String text;
    private Calendar time;

    public News(String text, Calendar time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
