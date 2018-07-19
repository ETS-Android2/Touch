package com.seeds.touch.Entity.Entities;

import com.google.gson.annotations.SerializedName;
import com.seeds.touch.Entity.Events.Event;

import java.util.Calendar;
import java.util.HashSet;

import static com.seeds.touch.Technical.Enums.*;

public class Item {
    @SerializedName("database_id")
    private String databaseID;
    @SerializedName("pictures")
    private HashSet<String> pictures;
    @SerializedName("release_date")
    private Calendar releaseDate;
    @SerializedName("tags")
    private HashSet<String> tags;
    @SerializedName("event")
    private Event event;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("attenders")
    private HashSet<String> attenders;
    @SerializedName("comments")
    private HashSet<Comment> comments;
    @SerializedName("rank")
    private float rank;
    @SerializedName("status")
    private Status status;
    @SerializedName("access_type")
    private AccessType accessType;
    @SerializedName("load_item")
    private boolean loadItem; //if was true, no data will be bind to it and just a progressbar will show inside it |  default=false

    public Item() {
        this.loadItem=false;
    }

    public Item(String databaseID, HashSet<String> pictures, Calendar releaseDate,
                HashSet<String> tags, Event event, String publisher, HashSet<String> attenders,
                HashSet<Comment> comments, float rank, Status status, AccessType accessType) {
        this.databaseID = databaseID;
        this.pictures = pictures;
        this.releaseDate = releaseDate;
        this.tags = tags;
        this.event = event;
        this.publisher = publisher;
        this.attenders = attenders;
        this.comments = comments;
        this.rank = rank;
        this.status = status;
        this.accessType = accessType;
        this.loadItem=false;
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

    public HashSet<String> getAttenders() {
        return attenders;
    }

    public void setAttenders(HashSet<String> attenders) {
        this.attenders = attenders;
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

    public boolean isLoadItem() {
        return loadItem;
    }

    public void setLoadItem(boolean loadItem) {
        this.loadItem = loadItem;
    }
}
