package com.movies;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Hyperlink;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoviePage {

    //left
    @FXML private ImageView posterImage; 
    @FXML private Label ratingLbl; 
    @FXML private Label viewLbl; 
    //center
    @FXML private Label movieNameLbl; 
    @FXML private Label yearLbl; 
    @FXML private Label directorNameLbl; 
    @FXML private Label genresLbl; 
    @FXML private Label durationLbl; 
    @FXML private Hyperlink trailerLink; 
    @FXML private Label movieOverviewLbl; 
    @FXML private Button commentBtn;
    //right
    @FXML private Button addToFavoritesBtn; 
    @FXML private Button chooseListBtn; 
    @FXML private VBox addToListVBox; 
    @FXML private Button addToWatchListBtn; 
    @FXML private Button recommendToBtn; 
    @FXML private VBox recommendToListVBox;  
    @FXML private Button markAsWatchedBtn; 
    @FXML private ImageView markAsWatchedIcon; 
    @FXML private Button backBtn;

    
    private Image unwatchedImage;
    private Image watchedImage;
    private Image notLikedImage;
    private Image likedImage;

    private User currentUser;
    private Movie currentMovie;

    private boolean isMovieLiked = false;
    private boolean isMovieWatched = false;

    //creates movie scene with topmenu bar
    public Scene createAboutMovieScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/movies/moviePage.fxml"));
        loader.setController(this);
        BorderPane root = loader.load();

        TopMenu topMenu = new TopMenu();
        root.setTop(topMenu.createTopMenu(primaryStage));

        return new Scene(root);
    }

    //MainPageden gelen kullanıcı ve film bilgilerini alan metot
    public void setData(User user, Movie movie) {
        this.currentUser = user;
        this.currentMovie = movie;
        
        isMovieLiked = DatabaseHandler.isInFavorites(currentUser.getUsername(), currentMovie.getId());
            if (isMovieLiked) {
                ((ImageView) addToFavoritesBtn.getGraphic()).setImage(likedImage);
            }

        isMovieWatched = DatabaseHandler.isInRecentWatches(currentUser.getUsername(), currentMovie.getId());
            if (isMovieWatched) {
                markAsWatchedIcon.setImage(watchedImage);
            }

        loadMovieDetailsFromAPI();
    }


    //this method gets all the info of the movie from API
    private void loadMovieDetailsFromAPI() {
        Integer movieId = currentMovie.getId();
        movieNameLbl.setText(currentMovie.getTitle());

        if (currentMovie.getPosterUrl()!=null && !currentMovie.getPosterUrl().isEmpty()) {
            try {
                posterImage.setImage(new Image(currentMovie.getPosterUrl(), true));
            } catch (Exception e) {
                System.out.println("error in poster getting");
            }
        }

        try {
            yearLbl.setText("(" + TmdbService.getYear(movieId) + ")");
            directorNameLbl.setText(TmdbService.getMovieDirector(movieId));
            durationLbl.setText(TmdbService.getDuration(movieId)/60 + "h " + TmdbService.getDuration(movieId)%60 + "m");
            movieOverviewLbl.setText(TmdbService.getSummary(movieId));
            double avgRating = DatabaseHandler.getRateAvarage(movieId);
            ratingLbl.setText(String.format("%.1f", avgRating));

            //comment count will be showed as view count
            int viewCount = DatabaseHandler.getCommentedUsersList(movieId).size();
            viewLbl.setText(String.valueOf(viewCount));

            ArrayList<String> genres = TmdbService.getMovieGenres(movieId);
            String genreText = "";

            for (int i = 0; i < genres.size(); i++) {
                genreText = genreText + genres.get(i); 
                if (i < genres.size() - 1) {
                    genreText = genreText + ", ";
                }
            }
            genresLbl.setText(genreText);

            
            String trailerUrl = TmdbService.getTrailer(movieId);

            trailerLink.setOnAction(e -> {
                try {
                    if (trailerUrl != null && !trailerUrl.trim().isEmpty()) {
                        java.awt.Desktop.getDesktop().browse(new java.net.URI(trailerUrl));   
                    } else {
                        System.out.println("cant find trailer");
                    }
                    
                } catch (Exception ex) {
                    System.out.println("couldnt open trailer " + ex.getMessage());
                }
            });

        } catch (Exception e) {
            System.out.println("error when getting movie info from API  " + e.getMessage());
        }
    }

    //at first the movie hasnt been watched or added to the favorites
    @FXML
    public void initialize() {
        addToListVBox.setVisible(false);
        addToListVBox.setManaged(false);
        unwatchedImage = new Image(getClass().getResourceAsStream("/com/movies/unwatched.png"));
        watchedImage = new Image(getClass().getResourceAsStream("/com/movies/watched.png"));
        markAsWatchedIcon.setImage(unwatchedImage);
        notLikedImage = new Image(getClass().getResourceAsStream("/com/movies/notLiked.png"));
        likedImage = new Image(getClass().getResourceAsStream("/com/movies/heart.png"));
        ImageView favIcon = (ImageView) addToFavoritesBtn.getGraphic();
        favIcon.setImage(notLikedImage);

        if (commentBtn != null) {
            commentBtn.setOnAction(event -> handleGoToComments());
        }
        if (backBtn != null) {
            backBtn.setOnAction(event -> handleGoToMain());
        }
    }

    private void handleGoToMain() {
        try {
            Stage currentStage = (Stage) backBtn.getScene().getWindow();
            MainPage mainPage = new MainPage();
            Scene mainScene = mainPage.createMainPageScene(currentStage);
            
            mainPage.setData(currentUser, null);
            
            currentStage.setScene(mainScene);
            currentStage.setFullScreen(true);

        } catch (Exception e) {
            System.out.println("cant go to mainPage");
            e.printStackTrace();
        }
    }

    //setData belki burada olucak
    private void handleGoToComments() {
        try {
            Stage currentStage = (Stage) commentBtn.getScene().getWindow();
            CommentsPage commentsPage = new CommentsPage();
            Scene commentsScene = commentsPage.createCommentsPageScene(currentStage);
            commentsPage.setData(currentUser, currentMovie);
            
            currentStage.setScene(commentsScene);
            currentStage.setFullScreen(true);

        } catch (Exception e) {
            System.out.println("cant move to comments page");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMarkAsWatched(ActionEvent event) {
        if (!isMovieWatched) {
            boolean success = DatabaseHandler.addMovieToRecentWatches(currentUser.getUsername(), currentMovie.getId());
            if (success) {
                isMovieWatched = true;
                markAsWatchedIcon.setImage(watchedImage);
            }
            
        } else {
            boolean success = DatabaseHandler.deleteMovieFromRecentWatches(currentUser.getUsername(), currentMovie.getId());
            if (success) {
                isMovieWatched = false;
                markAsWatchedIcon.setImage(unwatchedImage);
            }
        }
    }

    @FXML
    private void handleLike(ActionEvent event) {
        ImageView favIcon = (ImageView) addToFavoritesBtn.getGraphic();
        favIcon.setImage(likedImage);
        if (!isMovieLiked) {
            boolean added = DatabaseHandler.addMovieToFavorites(currentUser.getUsername(), currentMovie.getId());
            if (added) {
                isMovieLiked = true;
                favIcon.setImage(likedImage);
            }
        } else {
            boolean deleted = DatabaseHandler.deleteMovieFromFavorites(currentUser.getUsername(), currentMovie.getId());
            if (deleted) {
                isMovieLiked = false;
                favIcon.setImage(notLikedImage);
            }
        }
    }

    //changes the visibility of the "add to" menu
    //lists all the collections the user has
    @FXML
    private void handleChooseList(ActionEvent event) {
        boolean isVisible = addToListVBox.isVisible();
        addToListVBox.setVisible(!isVisible);
        addToListVBox.setManaged(!isVisible);

        if (!isVisible) {
            addToListVBox.getChildren().clear();
            return;
        }

        Label titleLbl = new Label("Add to...");
        titleLbl.setStyle("-fx-text-fill: white; -fx-padding: 5;");
        addToListVBox.getChildren().add(titleLbl);

        ArrayList<String> collectionsList = DatabaseHandler.getCollections(currentUser.getUsername());

        for (int i = 0; i < collectionsList.size(); i++) {
            String collectionName = collectionsList.get(i);
            
            Button collectionBtn = new Button(collectionName);
            collectionBtn.setPrefWidth(100);
            collectionBtn.setStyle("-fx-background-color: #282B35; -fx-text-fill: white; -fx-cursor: hand;");

            collectionBtn.setOnAction(e -> {
                boolean success = DatabaseHandler.addMovieToCollection(currentUser.getUsername(), currentMovie.getId(), collectionName);
                if (success) {
                    addToListVBox.setVisible(false);
                    addToListVBox.setManaged(false);
                } 
            });
            addToListVBox.getChildren().add(collectionBtn);
        }
    }

    //changes the visibility of the "recommend to" menu
    //lists the friends the user can recommend the movie to
    @FXML
    private void handleRecommendToFriend(ActionEvent event) {
        boolean isVisible = recommendToListVBox.isVisible();
        recommendToListVBox.setVisible(!isVisible);
        recommendToListVBox.setManaged(!isVisible);

        if (!isVisible) {
            recommendToListVBox.getChildren().clear();
            return;
        }

        Label titleLbl = new Label("Recommend to..");
        titleLbl.setStyle("-fx-text-fill: white; -fx-padding: 5;");
        recommendToListVBox.getChildren().add(titleLbl);

        ArrayList<String> friendsList = new ArrayList<>();
        
        ArrayList<String> followingsList = DatabaseHandler.getFollowings(currentUser.getUsername());
        for(int i = 0; i < followingsList.size(); i++) {
            if(DatabaseHandler.isFriend(currentUser.getUsername(),followingsList.get(i)))
                friendsList.add(followingsList.get(i));
        }
        

        for (int i = 0; i < friendsList.size(); i++) {
            String friendUsername = friendsList.get(i);
            
            Button friendBtn = new Button(friendUsername);
            friendBtn.setPrefWidth(100);
            friendBtn.setStyle("-fx-background-color: #282B35; -fx-text-fill: white; -fx-cursor: hand;");

            friendBtn.setOnAction(e -> {
                boolean success = DatabaseHandler.recomendToFriend(currentUser.getUsername(), friendUsername, currentMovie.getId());
                if (success) {
                    recommendToListVBox.setVisible(false);
                    recommendToListVBox.setManaged(false);
                } 
            });

            recommendToListVBox.getChildren().add(friendBtn);
        }
    }
    
}
