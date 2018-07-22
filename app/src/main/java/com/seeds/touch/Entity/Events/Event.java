package com.seeds.touch.Entity.Events;

import android.location.Location;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;

public class Event implements Serializable {
    private String title;
    private Calendar startDate;
    private Calendar endDate;
    private String location;
    private String description;
    private int ATTENDER_NUMBER_RANGE;

    public Event(String title, Calendar startDate, Calendar endDate, String location,
                 String description, int ATTENDER_NUMBER_RANGE) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.ATTENDER_NUMBER_RANGE = ATTENDER_NUMBER_RANGE;
    }

    public Event() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getStartDate() {
        return startDate;
    }

    public void setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getATTENDER_NUMBER_RANGE() {
        return ATTENDER_NUMBER_RANGE;
    }

    public void setATTENDER_NUMBER_RANGE(int ATTENDER_NUMBER_RANGE) {
        this.ATTENDER_NUMBER_RANGE = ATTENDER_NUMBER_RANGE;
    }

    public String getEventKey() {
        return this instanceof CinemaEvent ? "cinema" : (this instanceof RestaurantEvent ? "restaurant" : "trip");
    }
}
