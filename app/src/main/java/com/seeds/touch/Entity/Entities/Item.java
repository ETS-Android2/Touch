package com.seeds.touch.Entity.Entities;

import com.seeds.touch.Entity.Events.Event;

import java.util.Calendar;
import java.util.HashSet;

import static com.seeds.touch.Technical.Enums.*;

public class Item {
    private String databaseID;
    private HashSet<String> pictures;
    private Calendar releaseDate;
    private HashSet<String> tags;
    private Event event;
    private String publisher;
    private HashSet<String> attenderPeople;
    private HashSet<Comment> comments;
    private float rank;
    private Status status;
    private AccessType accessType;

    public Item(String databaseID, HashSet<String> pictures, Calendar releaseDate,
                HashSet<String> tags, Event event, String publisher, HashSet<String> attenderPeople,
                HashSet<Comment> comments, float rank, Status status, AccessType accessType) {
        this.databaseID = databaseID;
        this.pictures = pictures;
        this.releaseDate = releaseDate;
        this.tags = tags;
        this.event = event;
        this.publisher = publisher;
        this.attenderPeople = attenderPeople;
        this.comments = comments;
        this.rank = rank;
        this.status = status;
        this.accessType = accessType;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }

    public HashSet<String> getPictures() {
        return pictures;
    }

    public void setPictures(HashSet<String> pictures) {
        this.pictures = pictures;
    }

    public Calendar getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Calendar releaseDate) {
        this.releaseDate = releaseDate;
    }

    public HashSet<String> getTags() {
        return tags;
    }

    public void setTags(HashSet<String> tags) {
        this.tags = tags;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public HashSet<String> getAttenderPeople() {
        return attenderPeople;
    }

    public void setAttenderPeople(HashSet<String> attenderPeople) {
        this.attenderPeople = attenderPeople;
    }

    public HashSet<Comment> getComments() {
        return comments;
    }

    public void setComments(HashSet<Comment> comments) {
        this.comments = comments;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public AccessType getAccessType() {
        return accessType;
    }

    public void setAccessType(AccessType accessType) {
        this.accessType = accessType;
    }
}
