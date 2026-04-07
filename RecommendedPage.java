package com.movies;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecommendedPage {
    public Scene choose(Stage primaryStage, Integer movieId) {
        // Get Movie Details from TMDB
        String title = TmdbService.getMovieName(movieId);
        String summary = TmdbService.getSummary(movieId);
        String posterPath = TmdbService.getMoviePhotoUrl(movieId);
        String fullPosterUrl = "https://image.tmdb.org/t/p/w500" + posterPath;
        //Top Menu
        HBox topMenu = new TopMenu().createTopMenu(primaryStage);
        
        // Movie Poster
        ImageView posterView = new ImageView(new Image(fullPosterUrl));
        posterView.setFitWidth(400);
        posterView.setPreserveRatio(true);

        // Movie Info
        Label filmTitle = new Label(title.toUpperCase());
        filmTitle.setStyle("-fx-text-fill: #EAEAEA; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        Label aboutFilm = new Label(summary);
        aboutFilm.setWrapText(true);
        aboutFilm.setMaxWidth(600);
        aboutFilm.setStyle("-fx-text-fill: #EAEAEA; -fx-font-size: 14px;");

        // Adding movies to watchlist
        Button addWatchlist = new Button("Add to watchlist");
        addWatchlist.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
        addWatchlist.setOnAction(e -> {
            // DatabaseHandler is called to get movie
            DatabaseHandler.addMovieToCollection("currentUser", movieId, "watchlist");
            addWatchlist.setText("Added!");
            addWatchlist.setDisable(true);
        });

        VBox right = new VBox(20, filmTitle, aboutFilm, addWatchlist);
        HBox movieContainer = new HBox(40, posterView, right);
        
        VBox mainLayout = new VBox(30, topMenu, movieContainer);
        mainLayout.setStyle("-fx-background-color: #0B0F1A;");

        return new Scene(mainLayout, 1200, 800);
    }
}
