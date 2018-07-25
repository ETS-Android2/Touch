package com.seeds.touch.Entity.Entities;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seeds.touch.Technical.Enums.Gender;
import com.seeds.touch.Technical.GSON_Wrapper;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;

public class Person implements Serializable {
    private String databaseID;
    private String ID;
    private String name;
    private String lastName;
    private String GSONGender;
    private String GSONBirthdate;
    private String phoneNumber;
    private String biography;
    private String GSONLocation;
    private String macAddress;
    private String GSONFollowers;
    private String GSONFollowings;
    private String GSONFollowersQueue;
    private String GSONFollowingsQueue;
    private String GSONItems;
    private String GSONProfilePictures;
    private String PushID;
    private String password;
    private static final Gson gson=GSON_Wrapper.getInstance();


    public Person()
    {

    }

    public Person(String databaseID, String ID, String name, String lastName, String GSONGender,
                  String GSONBirthdate, String phoneNumber, String biography, String GSONLocation,
                  String macAddress, String GSONFollowers, String GSONFollowings,
                  String GSONFollowersQueue, String GSONFollowingsQueue, String GSONItems,
                  String GSONProfilePictures, String password,String PushID) {
        this.databaseID = databaseID;
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.GSONGender = GSONGender;
        this.GSONBirthdate = GSONBirthdate;
        this.phoneNumber = phoneNumber;
        this.biography = biography;
        this.GSONLocation = GSONLocation;
        this.macAddress = macAddress;
        this.GSONFollowers = GSONFollowers;
        this.GSONFollowings = GSONFollowings;
        this.GSONFollowersQueue = GSONFollowersQueue;
        this.GSONFollowingsQueue = GSONFollowingsQueue;
        this.GSONItems = GSONItems;
        this.GSONProfilePictures = GSONProfilePictures;
        this.password = password;
        this.PushID=PushID;
    }

    public String getDatabaseID() {
        return databaseID;
    }

    public void setDatabaseID(String databaseID) {
        this.databaseID = databaseID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGSONGender(String GSONGender) {
        this.GSONGender = GSONGender;
    }

    public void setGSONBirthdate(String GSONBirthdate) {
        this.GSONBirthdate = GSONBirthdate;
    }

    public void setGSONLocation(String GSONLocation) {
        this.GSONLocation = GSONLocation;
    }

    public void setGSONFollowers(String GSONFollowers) {
        this.GSONFollowers = GSONFollowers;
    }

    public void setGSONFollowings(String GSONFollowings) {
        this.GSONFollowings = GSONFollowings;
    }

    public void setGSONFollowersQueue(String GSONFollowersQueue) {
        this.GSONFollowersQueue = GSONFollowersQueue;
    }

    public void setGSONFollowingsQueue(String GSONFollowingsQueue) {
        this.GSONFollowingsQueue = GSONFollowingsQueue;
    }

    public void setGSONItems(String GSONItems) {
        this.GSONItems = GSONItems;
    }

    public void setGSONProfilePictures(String GSONProfilePictures) {
        this.GSONProfilePictures = GSONProfilePictures;
    }

    ////////////////////////////

    public Gender getGender() {
        return gson.fromJson(GSONGender,Gender.class);
    }

    public Calendar getBirthdate() {
        return gson.fromJson(GSONBirthdate,Calendar.class);
    }

    public Location getLocation() {
        return gson.fromJson(GSONLocation,Location.class);
    }

    public HashSet<String> getFollowers() {
        return gson.fromJson(GSONFollowers,new TypeToken<HashSet<String>>(){}.getType());
    }

    public HashSet<String> getFollowings() {
        Log.d("GHJB","IS : "+GSONFollowings);
        return  gson.fromJson(GSONFollowings,new TypeToken<HashSet<String>>(){}.getType());
    }

    public HashSet<String> getFollowersQueue() {
        return  gson.fromJson(GSONFollowersQueue,new TypeToken<HashSet<String>>(){}.getType());
    }

    public HashSet<String> getFollowingsQueue() {
        return  gson.fromJson(GSONFollowingsQueue,new TypeToken<HashSet<String>>(){}.getType());
    }

    public Item getItems() {
        return gson.fromJson(GSONItems,Item.class);
    }

    public HashSet<String> getProfilePictures() {
        return  gson.fromJson(GSONProfilePictures,new TypeToken<HashSet<String>>(){}.getType());
    }
    ///////////////////
    public void setGender(Gender gender)
    {
        this.GSONGender=gson.toJson(gender);
    }
    public void setLocation(Location location)
    {
        this.GSONLocation=gson.toJson(location);
    }
    public void setBirthdate(Calendar calendar)
    {
        this.GSONBirthdate=gson.toJson(calendar);
    }


    ///////////////////////////

    public boolean isARawUser() {
        return this.getName()==null || this.getName().isEmpty() ||
                this.getLastName()==null || this.getLastName().isEmpty() ||
                this.getGender()==null ||
                this.getMacAddress()==null || this.getMacAddress().isEmpty() ||
                this.getLocation()==null;
    }

    public String getPushID() {
        return PushID;
    }

    public void setPushID(String pushID) {
        PushID = pushID;
    }
}
