package com.seeds.touch.Entity.Entities;

import android.location.Location;

import com.seeds.touch.Technical.Enums.Gender;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;

public class Person implements Serializable {
    private String databaseID;
    private String name;
    private String lastName;
    private Gender gender;
    private Calendar birthdate;
    private String phoneNumber;
    private String biography;
    private Location location;
    private String macAddress;
    private HashSet<String> followers;
    private HashSet<String> followings;
    private HashSet<String> followersQueue;
    private HashSet<String> followingsQueue;
    private HashSet<String> items;
    private HashSet<String> profilePictures;
    private String ID;
    private String password;


    public Person()
    {

    }
    public Person(String databaseID, String ID, String name, String lastName, Gender gender,
                  Calendar birthdate, String phoneNumber, String biography, Location location,
                  String macAddress, HashSet<String> followers, HashSet<String> followings,
                  HashSet<String> followersQueue, HashSet<String> followingsQueue,
                  HashSet<String> items, HashSet<String> profilePictures) {
        this.databaseID = databaseID;
        this.ID = ID;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.biography = biography;
        this.location = location;
        this.macAddress = macAddress;
        this.followers = followers;
        this.followings = followings;
        this.followersQueue = followersQueue;
        this.followingsQueue = followingsQueue;
        this.items = items;
        this.profilePictures = profilePictures;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Calendar getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Calendar birthdate) {
        this.birthdate = birthdate;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public HashSet<String> getFollowers() {
        return followers;
    }

    public void setFollowers(HashSet<String> followers) {
        this.followers = followers;
    }

    public HashSet<String> getFollowings() {
        return followings;
    }

    public void setFollowings(HashSet<String> followings) {
        this.followings = followings;
    }

    public HashSet<String> getFollowersQueue() {
        return followersQueue;
    }

    public void setFollowersQueue(HashSet<String> followersQueue) {
        this.followersQueue = followersQueue;
    }

    public HashSet<String> getFollowingsQueue() {
        return followingsQueue;
    }

    public void setFollowingsQueue(HashSet<String> followingsQueue) {
        this.followingsQueue = followingsQueue;
    }

    public HashSet<String> getItems() {
        return items;
    }

    public void setItems(HashSet<String> items) {
        this.items = items;
    }

    public HashSet<String> getProfilePictures() {
        return profilePictures;
    }

    public void setProfilePictures(HashSet<String> profilePictures) {
        this.profilePictures = profilePictures;
    }

    public boolean isARawUser() {
        return this.getName()==null || this.getName().isEmpty() ||
                this.getLastName()==null || this.getLastName().isEmpty() ||
                this.getGender()==null ||
                this.getMacAddress()==null || this.getMacAddress().isEmpty() ||
                this.getLocation()==null;
    }
}
