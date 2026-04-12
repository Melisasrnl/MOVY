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

public class Followers {

    public Scene createFollowersScene(Stage stage) {
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);
        VBox root = new VBox(10);
        root.setPrefWidth(400);
        root.getChildren().add(topMenu);

        Label title = new Label("Followers");
        title.setStyle("-fx-font-size: 35px;");
        root.getChildren().add(title);

        VBox listVBox = new VBox(10);
        ArrayList<String> followingList = DatabaseHandler.getFollowings(App.currentUser.getUsername());
        ArrayList<String> followersList = DatabaseHandler.getFollowers(App.currentUser.getUsername());

        for (String follower : followersList) {
            HBox userRow = new HBox(10);
            String profilePic = DatabaseHandler.userStringGetter("profilepic", "username", follower);
            ProfilePhoto userPP = ProfilePhoto.fromString(profilePic);
            Circle userPPCircle = userPP.createCircle(18);

            VBox userInfo = new VBox(5);
            Label usernameLabel = new Label(follower);
            int followerCount = DatabaseHandler.userIntegerGetter("followercount", "username", follower);
            Label followerCountLabel = new Label( followerCount + " followers");
            userInfo.getChildren().addAll(usernameLabel, followerCountLabel);

            Button btn;
            if (followingList.contains(follower)) { //friends
                btn = new Button("Chat");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        //chat scene
                    }
                });
            }
            else { //follow back?
                btn = new Button("Follow Back");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        DatabaseHandler.followUser(App.currentUser.getUsername(), follower); 
                        HBox parent = (HBox) btn.getParent();
                        Button chatBtn = new Button("Chat");
                        chatBtn.setOnAction(new EventHandler<ActionEvent>() {
                            public void handle(ActionEvent e) {
                                ArrayList<String> members = new ArrayList<>();
                                members.add(App.currentUser.getUsername());
                                members.add(follower);
                                ArrayList<Boolean> roles = new ArrayList<>();
                                roles.add(false);
                                roles.add(false);
                                DatabaseHandler.createNewChat(follower, members,roles,true );
                                //stage.setScene(new InsideChat().createSearchChatsScene(stage)); //???
                            }
                        });
                        parent.getChildren().remove(btn);
                        parent.getChildren().add(chatBtn);
                    }
                });
            }
            HBox.setHgrow(userInfo, Priority.ALWAYS);
            HBox buttons = new HBox(5, btn);
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
