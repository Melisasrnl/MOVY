package com.movies;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.ArrayList;

import org.controlsfx.control.Rating;

public class CommentsPage {

    @FXML private VBox commentsContainer;
    @FXML private VBox bottomVBox;
    @FXML private HBox commentSendHBox;
    @FXML private TextArea commentArea;
    @FXML private Button addCommentBtn;
    @FXML private Button backBtn;
    @FXML private Rating starRating;

    private User  currentUser;
    private Movie currentMovie;

    public Scene createCommentsPageScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/movies/CommentsPage.fxml"));
        loader.setController(this);
        Parent root = loader.load();
        primaryStage.setFullScreen(true);
        Scene scene = new Scene(root);
        primaryStage.setTitle("Comments");
        
        return scene;
    }

    //moviePageden çağırılcak gibi şimdilik. 
    public void setData(User user, Movie movie) {
        this.currentUser  = user;
        this.currentMovie = movie;
        loadComments();
    }

    @FXML
    public void initialize() {
        addCommentBtn.setOnAction(e -> handleAddComment());
        if (backBtn != null) {
            backBtn.setOnAction(e -> {
                try {
                    Stage currentStage = (Stage) backBtn.getScene().getWindow();
                    MoviePage moviePage = new MoviePage();
                    
                    Scene movieScene = moviePage.createAboutMovieScene(currentStage);
                    currentStage.setScene(movieScene);
                    currentStage.setFullScreen(true);
                    
                } catch (Exception ex) {
                    System.out.println("cant go back to movie page");
                    ex.printStackTrace();
                }
            });
        }
    }

    //old comments
    private void loadComments() {
        commentsContainer.getChildren().clear();

        ArrayList<String> commenters = DatabaseHandler.getCommentedUsersList(currentMovie.getId());

        for (int i = 0; i < commenters.size(); i++) {
            String commenterUsername = commenters.get(i);

            Integer rate = DatabaseHandler.getRate(commenterUsername, currentMovie.getId());
            String commentText = DatabaseHandler.getComment(commenterUsername, currentMovie.getId());

            String ppURL = DatabaseHandler.userStringGetter("profilepic", "username", commenterUsername);
            if (ppURL != null && ppURL.equals("userstringnotfound")) {
                ppURL = null; 
            }

            addCommentCard(commenterUsername, ppURL, rate, commentText);
        }
    }

    //add comment button
    private void handleAddComment() {
        String text = commentArea.getText().trim();
        if (text.isEmpty()) return;
        int rating = (int) starRating.getRating();

        String username;
        String photoPath;
        if (currentUser != null) {
            username = currentUser.getUsername();
            photoPath = currentUser.getProfilePhoto().getUrl();
        } else {
            username = "guest";
            photoPath = ProfilePhoto.DEFAULT.getUrl();
        }

        boolean success = DatabaseHandler.newComment(username, text, rating, currentMovie.getId());
        if (success) {
            commentsContainer.getChildren().add(0, buildCommentCard(username, photoPath, rating, text));
            
            commentArea.clear();
            starRating.setRating(0);
        } 
    }

    // adds a comment card to the commend container
    private void addCommentCard(String username, String photoPath, double ratingValue, String commentText) {
        commentsContainer.getChildren().add(buildCommentCard(username, photoPath, ratingValue, commentText));
    }


    // returns comment card that has avatar, username, rating and comment
    private HBox buildCommentCard(String username, String photoPath, double ratingValue, String commentText) {

        //profile photo circle
        Circle avatar = new Circle(28);
        avatar.setStroke(Color.web("#2a3a5c"));
        avatar.setStrokeWidth(1.5);

        if (photoPath != null && !photoPath.isEmpty()) {
            try {
                avatar.setFill(new ImagePattern(new Image(photoPath, true)));
            } catch (Exception ignored) {
                avatar.setFill(Color.web("#1e2d47"));
            }
        } else {
            avatar.setFill(Color.web("#1e2d47"));
        }

        //username
        Label nameLabel = new Label(username);
        nameLabel.setStyle( "-fx-text-fill: white;" + "-fx-font-size: 15px;" + "-fx-font-weight: bold;");

        //star ratings
        Rating ratingDisplay = new Rating();
        ratingDisplay.setRating(ratingValue);
        ratingDisplay.setDisable(true);
        ratingDisplay.setStyle("-fx-opacity: 1;" +"-fx-background-color: #f5f0d8;" +"-fx-background-radius: 6;" +"-fx-padding: 2 8 2 8;");
        ratingDisplay.setPrefHeight(28);
        VBox.setMargin(ratingDisplay, new Insets(4, 0, 6, 0));

        //comment box
        Label commentLabel = new Label(commentText);
        commentLabel.setStyle("-fx-text-fill: white; -fx-font-size: 13px;");
        commentLabel.setWrapText(true);
        commentLabel.setMaxWidth(680);

        VBox commentBox = new VBox(commentLabel);
        commentBox.setStyle(
                "-fx-background-color: #0c111b;" +"-fx-border-color: #2a3a5c;" +"-fx-border-radius: 10;" + "-fx-background-radius: 10;");
        commentBox.setPadding(new Insets(12, 16, 28, 16));
        commentBox.setMaxWidth(720);

        //right side of the comment card
        VBox rightCol = new VBox(4, nameLabel, ratingDisplay, commentBox);
        rightCol.setAlignment(Pos.TOP_LEFT);
        HBox.setHgrow(rightCol, Priority.ALWAYS);

        //whole comment card
        HBox card = new HBox(14, avatar, rightCol);
        card.setAlignment(Pos.TOP_LEFT);
        card.setPadding(new Insets(6, 10, 6, 10));
        return card;
    }

}