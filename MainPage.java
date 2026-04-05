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

    public Scene createMainPageScene(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/movies/MainPage.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        root.setTop(TopMenu.createTopMenu(primaryStage, currentUser));

        primaryStage.setTitle("Movy");
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
            friendLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 11px;");

            friendInfo.getChildren().addAll(friendAvatar, friendLabel);
            card.getChildren().add(friendInfo);
        }

        
        card.setOnMouseClicked(e -> goToMoviePage(movie));
        card.setStyle("-fx-cursor: hand;");

        return card;
    }

    //goes to moviePage
    private void goToMoviePage(Movie movie) {
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

    //pretty scroll with animation
    private void scroll(ScrollPane scrollPane, double amount) {
        double contentWidth = scrollPane.getContent().getBoundsInLocal().getWidth();
        double viewWidth = scrollPane.getViewportBounds().getWidth();
        double scrollable = contentWidth - viewWidth;
        if (scrollable <= 0) return;

        double current = scrollPane.getHvalue();
        double step = amount / scrollable;
        double target = Math.max(0, Math.min(scrollPane.getHmax(), current + step));

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(350),new KeyValue(scrollPane.hvalueProperty(), target)));
        timeline.play();
    }

    //preadded movies
    private List<Movie> getPopularMovies() {
        Movie m1 = new Movie("The Notebook",     "1"); 
        m1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTk3OTM5Njg5M15BMl5BanBnXkFtZTYwMzA0ODI3._V1_.jpg");
        Movie m2 = new Movie("Eternal Sunshine",  "2"); 
        m2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTY4NzcwODg3Nl5BMl5BanBnXkFtZTcwNTEwOTMyMw@@._V1_.jpg");
        Movie m3 = new Movie("How to Lose a Guy in 10 Days","3"); 
        m3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMTQyNzMzNjA0NF5BMl5BanBnXkFtZTYwNzQyNTY3._V1_.jpg");
        return List.of(m1, m2, m3);
    }

    private List<Movie> getLatestMovies() {
        Movie m1 = new Movie("Inception",   "4"); m1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1_.jpg"); m1.setNew(true);
        Movie m2 = new Movie("Interstellar", "5"); m2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg");
        Movie m3 = new Movie("Dune",  "6"); m3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BN2FjNmEyNWMtYzM0ZS00NjIyLTg4YzYtYThlMGVjNzE1OGViXkEyXkFqcGdeQXVyMTkxNjUyNQ@@._V1_.jpg"); m3.setNew(true);
        return List.of(m1, m2, m3);
    }

    private List<Movie> getFriendsMovies() {
        User mina  = new User("mina",  "mina@mail.com",  2L);
        User bensu = new User("bensu", "bensu@mail.com", 3L);

        Movie m1 = new Movie("Interstellar", "5"); m1.setPosterUrl("https://m.media-amazon.com/images/M/MV5BZjdkOTU3MDktN2IxOS00OGEyLWFmMjktY2FiMmZkNWIyODZiXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"); m1.setRecommendedBy(mina); 
        Movie m2 = new Movie("Whiplash",   "7"); m2.setPosterUrl("https://m.media-amazon.com/images/M/MV5BOTA5NDZlZGUtMjAxOS00YTRkLTkwYmMtYWQ0NWEwZDZiNjEzXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_.jpg"); m2.setRecommendedBy(mina);  
        Movie m3 = new Movie("Catch Me If You Can", "8"); m3.setPosterUrl("https://m.media-amazon.com/images/M/MV5BOTY4ODY3OTctZTllNS00NTM4LWFlNzItYzgwYzkzNDFjYjEyXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg"); m3.setRecommendedBy(bensu); 
        return List.of(m1, m2, m3);
    }
}