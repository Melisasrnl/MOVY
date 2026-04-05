package com.movies;

import java.util.ArrayList;

public class Movie {

    protected String id;
    protected String title;
    protected int releaseYear;
    protected String director;
    protected ArrayList<Genre> genres;
    protected ArrayList<CommentsPage> comments;
    protected double averageRating;
    protected long numberOfViews;
    protected long numberOfFavorites;
    protected int duration;
    protected boolean isNew;
    protected String movieOverview;
    protected String movieFunFact;
    protected String movieTrailer; //URL
    
    public Movie(String movieTitle, String movieId) {
        this.id = movieId;
        this.title = movieTitle;
    }

    
}
