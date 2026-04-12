package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberSelectionGroupChat {

    private final String chatName;
    private final boolean isPrivate;

    public MemberSelectionGroupChat(String chatName, boolean isPrivate) {
        this.chatName = chatName;
        this.isPrivate = isPrivate;
    }

    public Scene createMemberSelectionGroupChatScene(Stage stage) {
        AnchorPane root = new AnchorPane();

        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        VBox contentBox = new VBox(15);
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 60.0);
        AnchorPane.setLeftAnchor(contentBox, 20.0);
        AnchorPane.setRightAnchor(contentBox, 20.0);
        AnchorPane.setBottomAnchor(contentBox, 20.0);

        HBox titleRow = new HBox(10);
        titleRow.setAlignment(Pos.CENTER_LEFT);

        Button backBtn = new Button("<");
        backBtn.setOnAction(e ->
                stage.setScene(new CreateNewGroupChat().createNewGroupChatScene(stage)));

        Label titleLbl = new Label("Choose Members");
        titleLbl.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        titleRow.getChildren().addAll(backBtn, titleLbl);

        Label chatInfoLbl = new Label(
                "Chat: " + chatName + "   |   " + (isPrivate ? "Private" : "Public"));
        chatInfoLbl.setStyle("-fx-font-size: 14px;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search users...");

        VBox usersBox = new VBox(10);
        usersBox.setPadding(new Insets(5));

        ScrollPane scrollPane = new ScrollPane(usersBox);
        scrollPane.setFitToWidth(true);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Button createBtn = new Button("Create Chat");
        createBtn.setPrefWidth(160);

        HBox bottomRow = new HBox();
        bottomRow.setAlignment(Pos.CENTER_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        bottomRow.getChildren().addAll(spacer, createBtn);

        ArrayList<UserSelectionRow> allRows = new ArrayList<>();
        ArrayList<String> allUsers = DatabaseHandler.getAllUsernames();

        for (String username : allUsers) {
            if (username.equals(App.currentUser.getUsername())) {
                continue;
            }

            UserSelectionRow row = new UserSelectionRow(username);
            allRows.add(row);
            usersBox.getChildren().add(row.container);
        }

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            usersBox.getChildren().clear();
            String text = newVal == null ? "" : newVal.trim().toLowerCase();

            for (UserSelectionRow row : allRows) {
                if (text.isEmpty() || row.username.toLowerCase().contains(text)) {
                    usersBox.getChildren().add(row.container);
                }
            }
        });

        createBtn.setOnAction(e -> {
            ArrayList<String> members = new ArrayList<>();
            ArrayList<Boolean> adminRoles = new ArrayList<>();

            members.add(App.currentUser.getUsername());
            adminRoles.add(true);

            for (UserSelectionRow row : allRows) {
                if (row.checkBox.isSelected()) {
                    members.add(row.username);
                    adminRoles.add(false);
                }
            }

            Integer createdChatId = DatabaseHandler.createNewChat(chatName, members, adminRoles, isPrivate);

            if (createdChatId == null) {
                showWarning("Chat could not be created.");
                return;
            }

            App.currentChat = createdChatId;
            stage.setScene(new InsideChat(createdChatId, App.currentUser.getUsername()).createInsideChatScene(stage));
        });

        contentBox.getChildren().addAll(titleRow, chatInfoLbl, searchField, scrollPane, bottomRow);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root, 800, 600);
    }

    private void showWarning(String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private static class UserSelectionRow {
        String username;
        HBox container;
        CheckBox checkBox;

        UserSelectionRow(String username) {
            this.username = username;
            this.checkBox = new CheckBox();

            Label nameLbl = new Label(username);
            nameLbl.setStyle("-fx-font-size: 15px;");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            this.container = new HBox(12);
            this.container.setAlignment(Pos.CENTER_LEFT);
            this.container.setPadding(new Insets(8));
            this.container.getChildren().addAll(nameLbl, spacer, checkBox);
        }
    }
}