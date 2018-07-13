package com.seeds.touch.Entity.Entities;

import java.util.Calendar;

public class Comment {
    private String text;
    private Calendar date;
    private String publisher;

    public Comment(String text, Calendar date, String publisher) {
        this.text = text;
        this.date = date;
        this.publisher = publisher;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
