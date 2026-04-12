package com.movies;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchFriends{
    private VBox resultContainer = new VBox();

    public void search(String username, Stage stage) {
        resultContainer.getChildren().clear();
        String checkUser = DatabaseHandler.userStringGetter("username", "username", username);
        if (!checkUser.equals("userstringnotfound")) {
            Button userButton = new Button(checkUser);
            userButton.setPrefSize(1200, 100);
            userButton.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
            userButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ProfilePage profilePage = new ProfilePage();
                    stage.setScene(profilePage.createProfilePageScene(stage));
                }
            });
            
            resultContainer.getChildren().add(userButton);
        } else {
            Label errorLabel = new Label("user not found");
            errorLabel.setStyle("-fx-text-fill: #FF5555; -fx-font-size: 16px;");
            resultContainer.getChildren().add(errorLabel);
        }
    }
    public Scene choose(Stage primaryStage) {
        HBox topMenu = new TopMenu().createTopMenu(primaryStage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);
        TextField searchFriend = new TextField();
        Button searchButton= new Button("Search");
        searchButton.setPrefSize(100, 50);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String userSearched = searchFriend.getText().trim();
                if (!userSearched.isEmpty()) {
                    search(userSearched, primaryStage);
                }
            }
        });
        HBox searchBox= new HBox(searchButton);
        searchButton.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchFriend.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
        VBox contains = new VBox(50, topMenu, searchBox, searchFriend);
        contains.setStyle("-fx-background-color: #0B0F1A;");
        contains.setMaxWidth(Double.MAX_VALUE);
        Scene testScene = new Scene(contains);
        return testScene;
    }
}