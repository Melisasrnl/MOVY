package com.movies;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CreateCollection {
    private String username;

    public CreateCollection(String username){
        this.username = username;
    }

    public StackPane createNewCol(Stage stage, AnchorPane root, CollectionPage cp){
    
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0,0,0,0.5);");

        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setStyle("-fx-background-color: #2c2727; -fx-background-radius: 10;");

        popup.setMaxWidth(300);
        popup.setMaxHeight(200);

        Label title = new Label("Create New Collection!");
        title.setTextFill(Color.WHITE);

        TextField input = new TextField();
        input.setPromptText("Enter name");

        HBox pButtons = new HBox(10);
        pButtons.setPadding(new Insets(10));
        ToggleButton publicBtn = new ToggleButton("Public");
        ToggleButton privateBtn = new ToggleButton("Private");
        pButtons.getChildren().addAll(publicBtn, privateBtn);

        ToggleGroup group = new ToggleGroup();
        publicBtn.setToggleGroup(group);
        privateBtn.setToggleGroup(group);

        privateBtn.setSelected(true);

        Button createButton = new Button("Create");
        createButton.setOnAction(e -> {
            String name = input.getText();
            if (name != null && !name.isBlank()) {
                boolean isPrivate = privateBtn.isSelected();
                boolean created = DatabaseHandler.newCollection(name, username, isPrivate);
                if (created) {
                    root.getChildren().remove(overlay);
                    stage.setScene(cp.createCollectionsPage(stage));
                }
            }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            root.getChildren().remove(overlay);
        });

        popup.getChildren().addAll(title, input, pButtons, createButton, cancelButton);
        overlay.getChildren().add(popup);
        overlay.setAlignment(Pos.CENTER);

        return overlay;
   } 
}
