package com.movies;

import java.util.HexFormat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GroupInfo{
    private Label nameLabel;
    private Label privacyLabel;
    private Label countLabel;
    private VBox memList;
    private ScrollPane scrollPane;
    private Button addMemberButton;
    private Button deleteGroup;
    private StackPane root;
    private Stage stage;
    private Scene previousScene;

    public GroupInfo(Stage stage, Scene previousScene) {
        this.stage = stage;
        this.previousScene = previousScene;
    }

    public Scene createGroupInfoScene() {
        HBox header = createInfoHeader();
        VBox members = createMemberBox();

        VBox mainContent = new VBox();
        mainContent.getChildren().addAll(header, members);

        root = new StackPane();
        root.getChildren().add(mainContent);

        return new Scene(root, 800, 600);
    }

    public HBox createInfoHeader(){

        HBox header = new HBox(5);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(12);
        header.setStyle("-fx-background-color: #2c2727;");
        header.setPadding(new Insets(10, 14, 10, 14));

        Button backButton = new Button("");
        backButton.setShape(new javafx.scene.shape.Polygon(0,12,24,0,24,24));
        backButton.setStyle("-fx-background-color: #b6b0b0c9;");

        backButton.setOnAction(e -> {
            stage.setScene(previousScene);
        });


        Circle avatar = new Circle(20);
        avatar.setFill(Color.GRAY);   

        VBox first = new VBox();
        first.setAlignment(Pos.CENTER);
        nameLabel = new Label("manifest");
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(20));
        privacyLabel = new Label("Private Chat");
        privacyLabel.setTextFill(Color.WHITE);
        privacyLabel.setFont(new Font(10));
        first.getChildren().addAll(nameLabel, privacyLabel);

        Label groupInfo = new Label("   Group Info   ");
        groupInfo.setTextFill(Color.WHITE);
        groupInfo.setFont(new Font(14));
        groupInfo.setStyle("-fx-background-color: #000000; -fx-background-radius: 15;");

        addMemberButton = new Button("Add Member");
        addMemberButton.setOnAction(e -> {
            showAddMemberPopup();
        });

        deleteGroup = new Button("Delete Group");

        Region spacer = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
    
        header.getChildren().addAll(backButton, avatar, first, spacer, groupInfo, spacer2, addMemberButton, deleteGroup);
        return header;
    }

    public VBox createMemberBox(){
        memList = new VBox();
        memList.setSpacing(10);
        memList.setPadding(new Insets(5));
        memList.setStyle("-fx-background-color: #ffffff;");
        createMemberRow("mina islam", true);
        createMemberRow("ekinsu", false);
        createMemberRow("melsa", true);

        return memList;
        
    }

    public void createMemberRow(String member, boolean isAdmin){
        HBox row = new HBox(10);

        Circle image = new Circle(20);
        image.setStyle("-fx-background-color: #ffffff;");

        Label name;
        if(isAdmin){
            name = new Label(member + " (admin)");
        }
        else{
            name = new Label(member);
        }
        name.setStyle("-fx-background-color: #ffffff;");
        name.setFont(new Font(20));

        Button infoButton = new Button("i");
        infoButton.setShape(new Circle(10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ContextMenu menu = new ContextMenu();

        MenuItem seeProfile = new MenuItem("See Profile");
        MenuItem makeAdmin = new MenuItem("Make Admin");
        MenuItem removeAdmin = new MenuItem("Remove Admin");

        makeAdmin.setDisable(isAdmin);
        removeAdmin.setDisable(!isAdmin);

        makeAdmin.setOnAction(e -> {
            name.setText(member + " (admin)");
        });

        removeAdmin.setOnAction(e -> {
            name.setText(member);
        });

        seeProfile.setOnAction(e -> {
        });

        menu.getItems().addAll(seeProfile, makeAdmin, removeAdmin);

        infoButton.setOnAction(e -> {
            menu.show(infoButton, Side.BOTTOM, 0, 0);
        });
        
        row.getChildren().addAll(image, name, spacer , infoButton);
        memList.getChildren().add(row);
        
    }

    private void showAddMemberPopup() {
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setStyle("-fx-background-color: #2c2727; -fx-background-radius: 10;");

        popup.setMaxWidth(300);
        popup.setMaxHeight(200);

        Label title = new Label("Add Member");
        title.setTextFill(Color.WHITE);

        TextField input = new TextField();
        input.setPromptText("Enter name");

        Button add = new Button("Add");
        add.setOnAction(e -> {
            String name = input.getText();
            if(!name.equals("")){
                createMemberRow(name, false);
                root.getChildren().remove(popup);
            }
        });
        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> {
            root.getChildren().remove(popup);
        });

        HBox buttons = new HBox(10, add, cancel);
        buttons.setAlignment(Pos.CENTER);

        popup.getChildren().addAll(title, input, buttons);

        StackPane.setAlignment(popup, Pos.CENTER);
        root.getChildren().add(popup);
    }

}
