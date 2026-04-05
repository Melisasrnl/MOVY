package com.movies;

import java.util.List;

public class User {
    
    private String username;
    private long userID;
    private String email;
    private String bio;
    private String profilePhotoPath; //url

    //movies will be pulled from the database by name or id
    //for now we will store it as String
    private List<String> favorites; 
    private List<String> watchlist;
    private List<String> watchedFilms;
    private List<String> dvdDrawer;

    private List<User> followers;
    private List<User> following;
    private List<User> friends;

    //assuming chats will be pulled by its id
    private List<Long> chats;

    public User(String aUserName, String anEmail, long userID) {
        this.username = aUserName;
        this.email = anEmail;
        this.userID = userID;
        this.profilePhotoPath = ProfilePhoto.DEFAULT.getUrl(); //default profile photo
    }

    //getters
    public String getUsername() {
        return username;
    }

    public long getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public List<String> getFavorites() {
        return favorites;
    }

    public List<String> getWatchlist() {
        return watchlist;
    }

    public List<String> getWatchedFilms() {
        return watchedFilms;
    }

    public List<String> getDvdDrawer() {
        return dvdDrawer;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<Long> getChats() {
        return chats;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }
 
    public void setProfilePhotoPath(String path) {
        this.profilePhotoPath = path;
    }
}
