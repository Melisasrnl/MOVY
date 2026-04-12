package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateNewGroupChat {

    public Scene createNewGroupChatScene(Stage stage) {
        AnchorPane root = new AnchorPane();

        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        VBox contentBox = new VBox(18);
        contentBox.setPadding(new Insets(25));
        contentBox.setAlignment(Pos.TOP_CENTER);
        AnchorPane.setTopAnchor(contentBox, 60.0);
        AnchorPane.setLeftAnchor(contentBox, 20.0);
        AnchorPane.setRightAnchor(contentBox, 20.0);
        AnchorPane.setBottomAnchor(contentBox, 20.0);

        HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("<");
        backBtn.setOnAction(e -> stage.setScene(new MyChats().createMyChatsScene(stage)));

        Label title = new Label("Create New Group Chat");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        titleRow.getChildren().addAll(backBtn, title);

        VBox formBox = new VBox(14);
        formBox.setMaxWidth(420);
        formBox.setAlignment(Pos.CENTER_LEFT);

        Label nameLbl = new Label("Chat Name");
        TextField chatNameField = new TextField();
        chatNameField.setPromptText("Enter group chat name");

        Label privacyLbl = new Label("Privacy");

        HBox privacyBox = new HBox(10);
        privacyBox.setAlignment(Pos.CENTER_LEFT);

        ToggleButton publicBtn = new ToggleButton("Public");
        ToggleButton privateBtn = new ToggleButton("Private");

        ToggleGroup privacyGroup = new ToggleGroup();
        publicBtn.setToggleGroup(privacyGroup);
        privateBtn.setToggleGroup(privacyGroup);
        publicBtn.setSelected(true);

        privacyBox.getChildren().addAll(publicBtn, privateBtn);

        Label infoLbl = new Label("You will be added as the first member and admin.");
        infoLbl.setStyle("-fx-text-fill: gray;");

        Button createBtn = new Button("Create Chat");
        createBtn.setPrefWidth(160);

        Button chooseMembersBtn = new Button("Choose Members");
        chooseMembersBtn.setPrefWidth(160);

        HBox buttonRow = new HBox(12);
        buttonRow.setAlignment(Pos.CENTER_LEFT);
        buttonRow.getChildren().addAll(createBtn, chooseMembersBtn);

        formBox.getChildren().addAll(
                nameLbl,
                chatNameField,
                privacyLbl,
                privacyBox,
                infoLbl,
                buttonRow
        );

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        contentBox.getChildren().addAll(titleRow, formBox, spacer);
        root.getChildren().addAll(topMenu, contentBox);

        createBtn.setOnAction(e -> {
            String chatName = chatNameField.getText() == null ? "" : chatNameField.getText().trim();

            if (chatName.isEmpty()) {
                showWarning("Chat name cannot be empty.");
                return;
            }

            ArrayList<String> members = new ArrayList<>();
            ArrayList<Boolean> adminRoles = new ArrayList<>();

            members.add(App.currentUser.getUsername());
            adminRoles.add(true);

            boolean isPrivate = privateBtn.isSelected();

            Integer createdChatId = DatabaseHandler.createNewChat(chatName, members, adminRoles, isPrivate);

            if (createdChatId == null) {
                showWarning("Chat could not be created.");
                return;
            }

            App.currentChat = createdChatId;
            stage.setScene(new InsideChat(createdChatId, App.currentUser.getUsername()).createInsideChatScene(stage));
        });

        chooseMembersBtn.setOnAction(e -> {
            String chatName = chatNameField.getText() == null ? "" : chatNameField.getText().trim();

            if (chatName.isEmpty()) {
                showWarning("Enter chat name first.");
                return;
            }

            boolean isPrivate = privateBtn.isSelected();

            stage.setScene(
                new MemberSelectionGroupChat(chatName, isPrivate).createMemberSelectionGroupChatScene(stage)
            );
        });

        return new Scene(root, 800, 600);
    }

    private void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}