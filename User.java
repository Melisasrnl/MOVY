package com.movies;

import java.util.ArrayList;
import java.util.List;

public class User {
    
    private String username;
    private long userID;
    private String email;
    private String bio;
    private ProfilePhoto profilePhoto; //url

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
        this.profilePhoto = ProfilePhoto.DEFAULT; //default profile photo
        this.bio = ""; //the default bio
        this.favorites = new ArrayList<>();
        this.watchlist = new ArrayList<>();
        this.watchedFilms = new ArrayList<>();
        this.dvdDrawer = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.chats = new ArrayList<>();
    }

    //getters
    public String getUsername() {
        return username;
    }

    public long getUserID() {
        return DatabaseHandler.userIntegerGetter("id", "username", this.username);
    }

    public String getEmail() {
        return DatabaseHandler.userStringGetter("email", "username", this.username);
    }

    public String getBio() {
        return DatabaseHandler.userStringGetter("userbio", "username", this.username);
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

    public ProfilePhoto getProfilePhoto() {
        return profilePhoto;
    }

    public int getFollowersCount() {
        return DatabaseHandler.userIntegerGetter("followercount", "username", this.username);
    }
    public int getFollowingCount() {
        return DatabaseHandler.userIntegerGetter("followingcount", "username", this.username);
    }
 
    public void setProfilePhotoPath(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
    // email,id,username cannot be set

    public void setProfilePhoto(ProfilePhoto profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setBio(String bio) {
        this.bio = bio;
        DatabaseHandler.userStringSetter("userbio", bio, "username", this.username);
    }

    public void setChats(List<Long> chats) {
        this.chats = chats;
    }

    public void setDvdDrawer(List<String> dvdDrawer) {
        this.dvdDrawer = dvdDrawer;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setWatchedFilms(List<String> watchedFilms) {
        this.watchedFilms = watchedFilms;
    }

    public void setWatchlist(List<String> watchlist) {
        this.watchlist = watchlist;
    }

    // helper methods for followers/following classes, these will not be used directly
    //instead, follow and unfollow will be used for consistent data
    public void addFollower(User targetUser) {
        this.followers.add(targetUser);
    }

    public void addFollowing(User targetUser) {
        this.following.add(targetUser);
    }

    public void removeFollower(User targetUser) {
        this.followers.remove(targetUser);
    }

    public void removeFollowing(User targetUser) {
        this.following.remove(targetUser);
    }
    
    //DB handler is also updated in follow and unfollow methods
    public void follow(User targetUser) {
        if (!this.following.contains(targetUser)) {
            this.addFollowing(targetUser);
            targetUser.addFollower(this);
            DatabaseHandler.followUser(this.username, targetUser.getUsername());
            
        }
    }
    
    public void unfollow(User targetUser) {
        if (this.following.contains(targetUser)) {
            this.removeFollowing(targetUser);
            targetUser.removeFollower(this);
            DatabaseHandler.stopFollowing(this.username, targetUser.getUsername());
        }
    }
}

