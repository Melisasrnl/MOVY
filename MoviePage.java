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
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MoviePage {

    //left
    @FXML private ImageView posterImage; 
    @FXML private Label ratingLbl; 
    @FXML private Label viewLbl; 
    @FXML private Label likeLbl; 
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
    @FXML private Button addToListBtn; 
    @FXML private VBox addToListVBox; 
    @FXML private Button addToWatchListBtn; 
    @FXML private Button recommendToBtn; 
    @FXML private VBox recommendToListVBox; 
    @FXML private Button aFriendBtn; 
    @FXML private Button markAsWatchedBtn; 
    @FXML private ImageView markAsWatchedIcon; 
    @FXML private Button backBtn;

    private boolean isMovieWatched = false;
    private Image unwatchedImage;
    private Image watchedImage;

    private boolean isMovieLiked = false;
    private Image notLikedImage;
    private Image likedImage;

    private User currentUser;
    private Movie currentMovie;

    //MainPageden gelen kullanıcı ve film bilgilerini alan metot
    public void setData(User user, Movie movie) {
        this.currentUser = user;
        this.currentMovie = movie;
        
        
        //later: if(currentMovie != null) movieNameLbl.setText(currentMovie.getTitle());
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
        isMovieWatched = !isMovieWatched;

        if (isMovieWatched) {
            markAsWatchedIcon.setImage(watchedImage);
        } else {
            markAsWatchedIcon.setImage(unwatchedImage);
        }
    }

    @FXML
    private void handleLike(ActionEvent event) {
        isMovieLiked = !isMovieLiked;
        ImageView favIcon = (ImageView) addToFavoritesBtn.getGraphic();
        if (isMovieLiked) {
            favIcon.setImage(likedImage);
        } else {
            favIcon.setImage(notLikedImage);
        }
    }

    //changes the visibility of the "add to" menu
    @FXML
    private void handleAddToList(ActionEvent event) {
        boolean isVisible = addToListVBox.isVisible();
        addToListVBox.setVisible(!isVisible);
        addToListVBox.setManaged(!isVisible);
    }

    //changes the visibility of the "recommend to" menu
    @FXML
    private void handleRecommendToFriend(ActionEvent event) {
        boolean isVisible = recommendToListVBox.isVisible();
        recommendToListVBox.setVisible(!isVisible);
        recommendToListVBox.setManaged(!isVisible);
    }

    //creates scene with topmenu bar
    public Scene createAboutMovieScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/movies/moviePage.fxml"));
        BorderPane root = loader.load();

        HBox topMenu = TopMenu.createTopMenu(primaryStage);
        root.setTop(topMenu); 

        Scene scene = new Scene(root);
        primaryStage.setTitle("About Movie Page");
        return scene;
    }
    
}
