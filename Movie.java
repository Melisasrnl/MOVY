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
    
    private String posterUrl;
    private User   recommendedBy; // arkadas satiri icin


    public Movie(String movieTitle, String movieId) {
        this.id = movieId;
        this.title = movieTitle;
    }


    public String getId() {
        return id;
    }
 
    public String getTitle() {
        return title;
    }
 
    public int getReleaseYear() {
        return releaseYear;
    }
 
    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }
 
    public String getDirector() {
        return director;
    }
 
    public void setDirector(String director) {
        this.director = director;
    }
 
    public ArrayList<Genre> getGenres() {
        return genres;
    }
 
    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
 
    public double getAverageRating() {
        return averageRating;
    }
 
    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
 
    public long getNumberOfViews() {
        return numberOfViews;
    }
 
    public void setNumberOfViews(long numberOfViews) {
        this.numberOfViews = numberOfViews;
    }
 
    public long getNumberOfFavorites() {
        return numberOfFavorites;
    }
 
    public void setNumberOfFavorites(long numberOfFavorites) {
        this.numberOfFavorites = numberOfFavorites;
    }
 
    public int getDuration() {
        return duration;
    }
 
    public void setDuration(int duration) {
        this.duration = duration;
    }
 
    public boolean isNew() {
        return isNew;
    }
 
    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }
 
    public String getMovieOverview() {
        return movieOverview;
    }
 
    public void setMovieOverview(String movieOverview) {
        this.movieOverview = movieOverview;
    }
 
    public String getMovieFunFact() {
        return movieFunFact;
    }
 
    public void setMovieFunFact(String movieFunFact) {
        this.movieFunFact = movieFunFact;
    }
 
    public String getMovieTrailer() {
        return movieTrailer;
    }
 
    public void setMovieTrailer(String movieTrailer) {
        this.movieTrailer = movieTrailer;
    }
 
    public String getPosterUrl() {
        return posterUrl;
    }
 
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
 
    public User getRecommendedBy() {
        return recommendedBy;
    }
 
    public void setRecommendedBy(User recommendedBy) {
        this.recommendedBy = recommendedBy;
    }
}
