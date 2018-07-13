package com.seeds.touch.Entity.Events;

import android.location.Location;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;

public class Event implements Serializable {
    private String title;
    private Calendar startDate;
    private Calendar endDate;
    private Location location;
    private String description;
    private HashSet<String> tags;
    private int ATTENDER_NUMBER_RANGE;

    public Event(String title, Calendar startDate, Calendar endDate, Location location,
                 String description, HashSet<String> tags, int ATTENDER_NUMBER_RANGE) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.description = description;
        this.tags = tags;
        this.ATTENDER_NUMBER_RANGE = ATTENDER_NUMBER_RANGE;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    public int getATTENDER_NUMBER_RANGE() {
        return ATTENDER_NUMBER_RANGE;
    }

    public void setATTENDER_NUMBER_RANGE(int ATTENDER_NUMBER_RANGE) {
        this.ATTENDER_NUMBER_RANGE = ATTENDER_NUMBER_RANGE;
    }
}
