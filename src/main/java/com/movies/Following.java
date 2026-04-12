package com.movies;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Following {

    public Scene createFollowingScene(Stage stage) {
        ArrayList<String> followingList = DatabaseHandler.getFollowings(App.currentUser.getUsername());
        ArrayList<String> followersList = DatabaseHandler.getFollowers(App.currentUser.getUsername());
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);
        VBox root = new VBox(10);
        root.setPrefWidth(400);
        root.getChildren().add(topMenu);

        Label title = new Label("Following");
        title.setStyle("-fx-font-size: 35px;");

        root.getChildren().add(title);

        VBox listVBox = new VBox(10);


        for (String following : followingList) {
            HBox userRow = new HBox(10);

            VBox userInfo = new VBox(5);
            Label usernameLabel = new Label(following);
            int followCount = DatabaseHandler.userIntegerGetter("followercount", "username", following);
            String profilePic = DatabaseHandler.userStringGetter("profilepic", "username", following);
            ProfilePhoto userPP = ProfilePhoto.fromString(profilePic);
            Circle userPPCircle = userPP.createCircle(18);

            Label followingCountLabel = new Label( followCount + " following");
            userInfo.getChildren().addAll(usernameLabel, followingCountLabel);

            final Button btn;
            if (followersList.contains(following)) { //friends
                btn = new Button("Chat");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //switch to chat scene
                    }
                });
            }
            else {
                btn = null;
            }
            Button unfbtn = new Button("Unfollow");
            unfbtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    DatabaseHandler.stopFollowing(App.currentUser.getUsername(), following);
                    listVBox.getChildren().remove(userRow); //removal from ui
                }
            });
        
            HBox.setHgrow(userInfo, Priority.ALWAYS);
            HBox buttons;
            if (btn!= null) {
                buttons = new HBox(5,btn,unfbtn);
            }
            else {
                buttons = new HBox(5,unfbtn);
            }
            userRow.getChildren().addAll(userPPCircle, userInfo, buttons);
            listVBox.getChildren().add(userRow);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(listVBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        return new Scene(root);
    }
}
