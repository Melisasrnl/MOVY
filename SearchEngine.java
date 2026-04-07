package com.movies;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchEngine {
   private static FlowPane movieDisplayArea;
   public Scene choose(Stage primaryStage, User currentUser) {
        // Main Container
        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #0B0F1A;");
        root.setPadding(new Insets(20));

        // Top Menu
        HBox topMenu = new TopMenu().createTopMenu(primaryStage);

        // Search Bar
        TextField searchField = new TextField();
        searchField.setPromptText("Search for a movie...");
        searchField.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA; -fx-prompt-text-fill: #EAEAEA;");

        // Filter the results by genre
        ComboBox<String> genreFilter = new ComboBox<>();
        genreFilter.getItems().addAll("All", "Action", "Comedy", "Drama", "Horror", "Romance");
        genreFilter.setValue("All");
        genreFilter.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA; -fx-font-weight: bold;");

        HBox filterBar = new HBox(15, new Label("Genre:"), genreFilter, searchBtn);
        filterBar.setAlignment(Pos.CENTER_LEFT);

        // Display the results
        movieDisplayArea = new FlowPane();
        movieDisplayArea.setHgap(20);
        movieDisplayArea.setVgap(20);
        movieDisplayArea.setAlignment(Pos.TOP_LEFT);
        
        ScrollPane scrollPane = new ScrollPane(movieDisplayArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: #0B0F1A; -fx-border-color: #0B0F1A;");

        // Setting the functionality of search button
        searchBtn.setOnAction(e -> {
            String query = searchField.getText();
            String genre = genreFilter.getValue();
            search(query, genre, primaryStage, currentUser);
        });

        root.getChildren().addAll(topMenu, searchField, filterBar, scrollPane);
        return new Scene(root, 1200, 800);
    }

    private static void search(String query, String genre, Stage stage, User user) {
        movieDisplayArea.getChildren().clear();
        
        //Get the initial list from TMDB
        ArrayList<Integer> movieIds;
        if (query == null || query.isEmpty()) {
            movieIds = TmdbService.getPopularMovies(); // Default to popular if empty
        } else {
            movieIds = TmdbService.searchMovie(query);
        }

        if (movieIds.isEmpty()) {
            movieDisplayArea.getChildren().add(new Label("No movies found for: " + query));
            return;
        }

        // Filter and Create Buttons
        for (Integer id : movieIds) {
            ArrayList<String> movieGenres = TmdbService.getMovieGenres(id);
            
            // Filter by genre if not "All"
            if (!genre.equals("All") && !movieGenres.contains(genre)) {
                continue;
            }

            Button movieBtn = createMovieButton(id, stage, user);
            movieDisplayArea.getChildren().add(movieBtn);
        }
    }

    private static Button createMovieButton(Integer movieId, Stage stage, User user) {
        Button btn = new Button();
        btn.setPrefSize(180, 270);
        
        // Load poster image from the db
        String path = TmdbService.getMoviePhotoUrl(movieId);
        String fullUrl = "https://image.tmdb.org/t/p/w500" + path;
        
        try {
            ImageView poster = new ImageView(new Image(fullUrl, 180, 270, true, true));
            btn.setGraphic(poster);
        } catch (Exception e) {
            btn.setText(TmdbService.getMovieName(movieId)); // Fallback to text
        }

        // Navigation Logic
        btn.setOnAction(e -> {
            try {
                Movie movie = new Movie(TmdbService.getMovieName(movieId), String.valueOf(movieId));
                // Populate movie object with details from TmdbService
                movie.setMovieOverview(TmdbService.getSummary(movieId));
                movie.setDirector(TmdbService.getMovieDirector(movieId));
                // create the movie object
                MoviePage moviePage = new MoviePage();
                Scene movieScene = moviePage.createAboutMovieScene(stage);
                moviePage.setData(user, movie);
                stage.setScene(movieScene);
                stage.setFullScreen(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        return btn;
    }
}
