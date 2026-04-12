package com.movies;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

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
import javafx.stage.Modality;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class EditProfile {
    public Scene createEditProfileScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        AnchorPane contentBox = new AnchorPane();
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 40.0);
        AnchorPane.setLeftAnchor(contentBox, 0.0);
        AnchorPane.setRightAnchor(contentBox, 0.0);
        AnchorPane.setBottomAnchor(contentBox, 0.0);

        Button returnToProfile = new Button("<");
        AnchorPane.setTopAnchor(returnToProfile, 0.0);
        AnchorPane.setLeftAnchor(returnToProfile, -15.0);

        ImageView userPPCircle = new ImageView(new Image(App.currentUser.getProfilePhoto().getUrl()));
        userPPCircle.setFitWidth(54);
        userPPCircle.setFitHeight(54);
        Circle clip = new Circle(27, 27, 27);
        userPPCircle.setClip(clip);

        AnchorPane.setTopAnchor(userPPCircle, 20.0);
        AnchorPane.setLeftAnchor(userPPCircle, 15.0);

        Label username = new Label(App.currentUser.getUsername());
        AnchorPane.setTopAnchor(username, 30.0);
        AnchorPane.setLeftAnchor(username, 80.0);

        Label bio = new Label(App.currentUser.getBio());
        AnchorPane.setTopAnchor(bio, 50.0);
        AnchorPane.setLeftAnchor(bio, 80.0);

        Button editBio = new Button("Edit");
        AnchorPane.setTopAnchor(editBio, 25.0);
        AnchorPane.setRightAnchor(editBio, 20.0);
        editBio.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("EDIT ABOUT ME");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                TextField editField = new TextField(bio.getText());
                editField.setMaxWidth(250);
                Button saveBtn = new Button("Save");
                saveBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        String newBio = editField.getText();
                        bio.setText(newBio);
                        popupStage.close();

                    }
                });

                newBox.getChildren().addAll(editField, saveBtn);
                Scene scene = new Scene(newBox, 300, 200);
                popupStage.setScene(scene);

                popupStage.show();
            }
        });

        Label PPChoosing = new Label("Choose a Profile Photo: ");
        AnchorPane.setTopAnchor(PPChoosing, 100.0);
        AnchorPane.setLeftAnchor(PPChoosing, 40.0);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        int col = 0, row = 0;
        ArrayList<Button> buttons = new ArrayList<Button>();
        for (ProfilePhoto photo : ProfilePhoto.values()) {
            ImageView img = new ImageView(new Image(photo.getUrl()));
            img.setFitWidth(50);
            img.setFitHeight(50);
            Button btn = new Button();
            btn.setGraphic(img);
            buttons.add(btn);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    App.currentUser.setProfilePhoto(photo);
                    for (Button b : buttons) {
                        b.setDisable(true);
                    }
                }
            });

            grid.add(btn, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }

        AnchorPane.setTopAnchor(grid, 130.0);
        AnchorPane.setLeftAnchor(grid, 40.0);

        Button deleteAccount = new Button("Delete Account");
        AnchorPane.setBottomAnchor(deleteAccount, 10.0);
        AnchorPane.setRightAnchor(deleteAccount, 10.0);

        deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("DELETING ACCOUNT");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                Label label = new Label(
                        "You are deleting this account. If you delete this account, all of your data will be lost permanently. Are you sure you want to delete this account?");
                label.setWrapText(true);
                label.setMaxWidth(250);
                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                    }
                });
                Button deleteBtn = new Button("Delete Account");
                deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                        DatabaseHandler.deleteUser(App.currentUser.getUsername());
                        //stage.setScene(new StartingPage().start(stage));
                    }
                });

                newBox.getChildren().addAll(label, cancelBtn, deleteBtn);

                Scene scene = new Scene(newBox, 300, 200);
                popupStage.setScene(scene);

                popupStage.show();
            }
        });

        contentBox.getChildren().addAll(returnToProfile, userPPCircle, username, bio, PPChoosing, grid,
                deleteAccount,
                editBio);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root);
    }
}