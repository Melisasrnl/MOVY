package com.movies;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;

public class TopMenu {
    // returns the topmenu
    public  HBox createTopMenu(Stage stage) {
        HBox topMenu = new HBox(10);
        topMenu.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        topMenu.setPadding(new Insets(5));
        topMenu.setAlignment(Pos.CENTER_LEFT);

        ProfilePhoto userPP = App.currentUser.getProfilePhoto();
        Circle userPPCircle = userPP.createCircle(18);

        Button userButton = new Button(App.currentUser.getUsername());
        userButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        userButton.setPrefSize(83, 40);
        userButton.setMaxWidth(Double.MAX_VALUE);
        userButton.setPadding(new Insets(0, 10, 0, 0));
        userButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                stage.setScene(new ProfilePage().createProfilePageScene(stage));
            }
        });
        Button homeButton = new Button("H");
        homeButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        homeButton.setPrefSize(26, 40);
        homeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                try {
                    MainPage mainPage = new MainPage();
                    Scene mainScene = mainPage.createMainPageScene(stage);                   
                    mainPage.setData(App.currentUser, null); 
                    stage.setScene(mainScene);
                } catch (Exception e) {
                    System.out.println("error when going to homePage from topMenu " + e.getMessage());
                }
            }
        });

        Button searchFriendsButton = new Button("Search Friends");
        searchFriendsButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        searchFriendsButton.setPrefSize(116, 40);
        searchFriendsButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(new SearchFriends().choose(stage));
            }
        });
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        searchButton.setPrefSize(66, 40);
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
              stage.setScene(new SearchEngine().choose(stage, App.currentUser));
            }
        });

        MenuButton chatsMenuButton = new MenuButton("Chats");
        chatsMenuButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        chatsMenuButton.setPrefSize(109, 40);
        MenuItem searchChats = new MenuItem("Search Chats");
        searchChats.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        MenuItem myChats = new MenuItem("My Chats");
        chatsMenuButton.getItems().addAll(searchChats, myChats);
        chatsMenuButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");

        searchChats.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(new SearchChats().createSearchChatsScene(stage));
            }
        });

        myChats.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(new MyChats().createMyChatsScene(stage));
            }
        });

        Button recommendButton = new Button("Recommend Me!");
        recommendButton.setStyle("-fx-background-color: #0B0F1A; -fx-text-fill: white;");
        recommendButton.setPrefSize(134, 40);
        recommendButton.setPadding(new Insets(0, 0, 0, 10));
        recommendButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.setScene(new TestPage().choose(stage));
            }
        });
        topMenu.getChildren().addAll(userPPCircle, userButton, homeButton, searchFriendsButton, searchButton,
                chatsMenuButton, recommendButton);
        return topMenu;
    }

}