package com.seeds.touch.Entity.Entities;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seeds.touch.Entity.Events.CinemaEvent;
import com.seeds.touch.Entity.Events.Event;
import com.seeds.touch.Entity.Events.RestaurantEvent;
import com.seeds.touch.Entity.Events.TripEvent;
import com.seeds.touch.Technical.GSON_Wrapper;
import com.seeds.touch.Technical.Helper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashSet;

import static com.seeds.touch.Technical.Enums.*;


//all json fileds are set till now :)
public class Item {
    private final static Gson gson = GSON_Wrapper.getInstance();
    private String databaseID;
    private String JSONPictures;
    private String JSONReleaseDate;
    private String JSONTags;
    private String JSONEvent;
    private String publisher;
    private String JSONAttenders;
    private String JSONComments;
    private String eventKey;
    private float rank;
    private String JSONStatus;
    private String JSONAccessType;
    private boolean loadItem; //if was true, no data will be bind to it and just a progressbar will show inside it |  default=false

    public Item() {
        this.loadItem = false;
    }

    public Item(String databaseID, String JSONPictures, String JSONReleaseDate, String JSONTags,
                String JSONEvent, String publisher, String JSONAttenders, String JSONComments,
                float rank, String JSONStatus, String JSONAccessType, String eventKey) {
        this.databaseID = databaseID;
        this.JSONPictures = JSONPictures;
        this.JSONReleaseDate = JSONReleaseDate;
        this.JSONTags = JSONTags;
        this.JSONEvent = JSONEvent;
        this.publisher = publisher;
        this.JSONAttenders = JSONAttenders;
        this.JSONComments = JSONComments;
        this.rank = rank;
        this.JSONStatus = JSONStatus;
        this.JSONAccessType = JSONAccessType;
        this.loadItem = false;
        this.eventKey = eventKey;
    }

    public Calendar getReleaseDate() {
        return gson.fromJson(JSONReleaseDate, Calendar.class);
    }

    public Event getEvent(String type) {
        if (JSONEvent == null)
            return null;
        Log.d("VBV", JSONEvent);
        if (type.equalsIgnoreCase("cinema"))
            return gson.fromJson(JSONEvent, CinemaEvent.class);
        else if (type.equalsIgnoreCase("trip"))
            return gson.fromJson(JSONEvent, TripEvent.class);
        else if (type.equalsIgnoreCase("restaurant"))
            return gson.fromJson(JSONEvent, RestaurantEvent.class);
        return null;
    }

    public Status getStatus() {
        return gson.fromJson(JSONStatus, Status.class);
    }

    public AccessType getAccessType() {
        return gson.fromJson(JSONAccessType, AccessType.class);
    }

    public HashSet<String> getPictures() {
        return gson.fromJson(JSONPictures, new TypeToken<HashSet<String>>() {
        }.getType());
    }


    public HashSet<String> getTags() {
        return gson.fromJson(JSONTags, new TypeToken<HashSet<String>>() {
        }.getType());
    }


    public HashSet<String> getAttenders() {
        return gson.fromJson(JSONAttenders, new TypeToken<HashSet<String>>() {
        }.getType());
    }


    public HashSet<String> getComments() {
        return gson.fromJson(JSONComments, new TypeToken<HashSet<String>>() {
        }.getType());
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }

    public void setJSONPictures(String JSONPictures) {
        this.JSONPictures = JSONPictures;
    }

    public void setJSONReleaseDate(String JSONReleaseDate) {
        this.JSONReleaseDate = JSONReleaseDate;
    }

    public void setJSONTags(String JSONTags) {
        this.JSONTags = JSONTags;
    }

    public void setJSONEvent(String JSONEvent) {
        this.JSONEvent = JSONEvent;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setJSONAttenders(String JSONAttenders) {
        this.JSONAttenders = JSONAttenders;
    }

    public void setJSONComments(String JSONComments) {
        this.JSONComments = JSONComments;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public void setJSONStatus(String JSONStatus) {
        this.JSONStatus = JSONStatus;
    }

    public void setJSONAccessType(String JSONAccessType) {
        this.JSONAccessType = JSONAccessType;
    }

    public void setLoadItem(boolean loadItem) {
        this.loadItem = loadItem;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public boolean isLoadItem() {
        return loadItem;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
