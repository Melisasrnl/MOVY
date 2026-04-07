package com.movies;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class MainPage {

    private User currentUser;
    private Movie currentMovie;

    public void setData(User user, Movie movie) {
        this.currentUser = user;
        this.currentMovie = movie;
        loadRows();
    }

    @FXML private HBox popularRow;
    @FXML private ScrollPane popularScroll;
    @FXML private Button popularLeftBtn;
    @FXML private Button popularRightBtn;

    @FXML private HBox latestRow;
    @FXML private ScrollPane latestScroll;
    @FXML private Button latestLeftBtn;
    @FXML private Button latestRightBtn;

    @FXML private HBox friendsRow;
    @FXML private ScrollPane friendsScroll;
    @FXML private Button friendsLeftBtn;
    @FXML private Button friendsRightBtn;

    private Stage stage;

    private static final double POSTER_WIDTH = 140.0;
    private static final double POSTER_HEIGHT = 210.0;
    private static final double SCROLL_AMOUNT = 400.0;
    private final String DEFAULT_MOVIE_POSTER = "https://img.freepik.com/premium-photo/vertical-dark-red-paper-texture-with-noise-speckles_469558-46611.jpg";

    //returns main page scene with top menu
    public Scene createMainPageScene(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/movies/MainPage.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();
        TopMenu topMenu = new TopMenu();
        root.setTop(topMenu.createTopMenu(primaryStage));

        return new Scene(root);
    }

    //buttons for every movie row
    @FXML
    public void initialize() {
        popularLeftBtn.setOnAction(e -> scroll(popularScroll, -SCROLL_AMOUNT));
        popularRightBtn.setOnAction(e -> scroll(popularScroll, SCROLL_AMOUNT));
        latestLeftBtn.setOnAction(e -> scroll(latestScroll, -SCROLL_AMOUNT));
        latestRightBtn.setOnAction(e -> scroll(latestScroll,  SCROLL_AMOUNT));
        friendsLeftBtn.setOnAction(e -> scroll(friendsScroll, -SCROLL_AMOUNT));
        friendsRightBtn.setOnAction(e -> scroll(friendsScroll, SCROLL_AMOUNT));
    }

    //load rows with movies
    private void loadRows() {
        List<Movie> popularMovies = getPopularMovies();
        List<Movie> latestMovies = getLatestMovies();
        List<Movie> friendsMovies = getFriendsMovies();
        fillRow(popularRow,popularMovies,false);
        fillRow(latestRow, latestMovies, false);
        fillRow(friendsRow, friendsMovies, true);  
    }

    //fill rows with poster cars
    private void fillRow(HBox row, List<Movie> movies, boolean showFriendInfo) {
        row.getChildren().clear();
        for (Movie movie : movies) {
            VBox card = buildPosterCard(movie, showFriendInfo);
            row.getChildren().add(card);
        }
    }

    //returns whole poster card (with friend info(from friends row))
    private VBox buildPosterCard(Movie movie, boolean showFriendInfo) {
        // movie poster
        ImageView poster = new ImageView();
        poster.setFitWidth(POSTER_WIDTH);
        poster.setFitHeight(POSTER_HEIGHT);
        poster.setPreserveRatio(false);

        //add poster immage or add grey placeholder 
        if (movie.getPosterUrl() != null && !movie.getPosterUrl().isEmpty()) {
            try {
                poster.setImage(new Image(movie.getPosterUrl(), true));
            } catch (Exception ignored) {}
        }

        
        Rectangle clip = new Rectangle(POSTER_WIDTH, POSTER_HEIGHT);
        clip.setArcWidth(10);
        clip.setArcHeight(10);
        poster.setClip(clip);
        //card
        VBox card = new VBox(6);
        card.setAlignment(Pos.TOP_CENTER);
        card.getChildren().add(poster);

        //adds friend info 
        if (showFriendInfo && movie.getRecommendedBy() != null) {
            HBox friendInfo = new HBox(6);
            friendInfo.setAlignment(Pos.CENTER_LEFT);

            //friend profile photo
            javafx.scene.shape.Circle friendAvatar = new javafx.scene.shape.Circle(14);
            friendAvatar.setFill(Color.web("#1e2d47"));
            friendAvatar.setStroke(Color.web("#2a3a5c"));
            if (movie.getRecommendedBy().getProfilePhoto() != null) {
                try {
                    friendAvatar.setFill(new javafx.scene.paint.ImagePattern(
                        new Image(movie.getRecommendedBy().getProfilePhoto().getUrl(), true)));
                } catch (Exception ignored) {}
            }

            Label friendLabel = new Label(
            movie.getRecommendedBy().getUsername());
            friendLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 12px;");

            friendInfo.getChildren().addAll(friendAvatar, friendLabel);
            card.getChildren().add(friendInfo);
        }

        
        card.setOnMouseClicked(e -> goToMoviePage(movie));
        card.setStyle("-fx-cursor: hand;");

        return card;
    }

    //goes to moviePage
    public void goToMoviePage(Movie movie) {
        try {
            MoviePage moviePage = new MoviePage();
            Scene movieScene = moviePage.createAboutMovieScene(stage);
            moviePage.setData(currentUser, movie);
            stage.setScene(movieScene);
            stage.setFullScreen(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void scroll(ScrollPane scrollPane, double amount) {
        double totalWidth = scrollPane.getContent().getBoundsInLocal().getWidth();
        double visibleWidth = scrollPane.getViewportBounds().getWidth();

        double maxScrollableWidth = totalWidth - visibleWidth;
        if (maxScrollableWidth <= 0) {
            return;
        }
        double stepRatio = amount / maxScrollableWidth;
        double newPosition = scrollPane.getHvalue() + stepRatio;

        if (newPosition < 0.0) {
            newPosition = 0.0;
        }
        if (newPosition > 1.0) {
            newPosition = 1.0;
        }
        scrollPane.setHvalue(newPosition);  

    }

    
    private List<Movie> getPopularMovies() {
        ArrayList<Movie> popularMovies = new ArrayList<>();
        
        ArrayList<Integer> popularsIDs = TmdbService.getPopularMovies();

        int movieNumber = Math.min(20, popularsIDs.size());

        for (int i = 0; i < movieNumber; i++) {
            Integer movieId = popularsIDs.get(i);

            String movieName = "Unknown Title";
            String moviePosterUrl = DEFAULT_MOVIE_POSTER;

            try {
                movieName = TmdbService.getMovieName(movieId);
                String moviePosterPath = TmdbService.getMoviePhotoUrl(movieId);
                if (moviePosterPath != null && !moviePosterPath.equals("null") && !moviePosterPath.isEmpty()) {
                    moviePosterUrl = "https://image.tmdb.org/t/p/w500" + moviePosterPath;
                }
            } catch (Exception e) {
                System.out.println("cant get popular movie poster");
            }
            

            Movie movie = new Movie(movieName, movieId);
            movie.setPosterUrl(moviePosterUrl);
            popularMovies.add(movie);
        }
        return popularMovies;
    }

    private List<Movie> getLatestMovies() {
        ArrayList<Movie> latestMovies = new ArrayList<>();
        
        ArrayList<Integer> latestsIDs = TmdbService.getLatestReleasesMovies();

        int movieNumber = Math.min(20, latestsIDs.size());

        for (int i = 0; i < movieNumber; i++) {
            Integer movieId = latestsIDs.get(i);

            String movieName = "Unknown Title";
            String moviePosterUrl = DEFAULT_MOVIE_POSTER;

            try {
                movieName = TmdbService.getMovieName(movieId);
                String moviePosterPath = TmdbService.getMoviePhotoUrl(movieId);
                if (moviePosterPath != null && !moviePosterPath.equals("null") && !moviePosterPath.isEmpty()) {
                    moviePosterUrl = "https://image.tmdb.org/t/p/w500" + moviePosterPath;
                }

            } catch (Exception e) {
                System.out.println("cant get latest movie poster");
            }
            Movie movie = new Movie(movieName, movieId);
            movie.setPosterUrl(moviePosterUrl);
            latestMovies.add(movie);
            
        }
        return latestMovies;
        
    }

    //returns arraylist of recommended movies by friends
    private List<Movie> getFriendsMovies() {
        ArrayList<Movie> recommendedMovies = new ArrayList<>();
        if (currentUser == null) {
            return recommendedMovies;
        }
        String currentUsersName = currentUser.getUsername();
        ArrayList<Integer> recommendedMovieIds = DatabaseHandler.getMoviesFromRecomendedByFriends(currentUsersName);

        for (int i = 0; i < recommendedMovieIds.size(); i++) {
            Integer recommendedMovieId = recommendedMovieIds.get(i);

            String friendUsername = DatabaseHandler.whoRecomended(currentUsersName, recommendedMovieIds.get(i));
            String friendEmail = DatabaseHandler.userStringGetter("email", "username", friendUsername);
            User friend = new User(friendUsername, friendEmail);
            String ppURL = DatabaseHandler.userStringGetter("profilepic", "username", friendUsername);
            friend.setProfilePhoto(ProfilePhoto.fromString(ppURL));

            String recommendedMovieName = "unknown title";
            String moviePosterUrl = DEFAULT_MOVIE_POSTER;

            try {
                recommendedMovieName = TmdbService.getMovieName(recommendedMovieId);
                String moviePosterPath = TmdbService.getMoviePhotoUrl(recommendedMovieId);
                if (moviePosterPath != null && !moviePosterPath.equals("null") && !moviePosterPath.isEmpty()) {
                    moviePosterUrl = "https://image.tmdb.org/t/p/w500" + moviePosterPath;
                }
            } catch (Exception e) {
                System.out.println("cant get recommended movie poster");
            }
            

            Movie recommendedMovie = new Movie( recommendedMovieName , recommendedMovieId);
            recommendedMovie.setPosterUrl(moviePosterUrl);
            recommendedMovie.setRecommendedBy(friend); 
            
            recommendedMovies.add(recommendedMovie);
        }

        return recommendedMovies;
    }
}