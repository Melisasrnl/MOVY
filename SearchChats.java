package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class SearchChats {

    public Scene createSearchChatsScene(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #0B0F1A;");

        HBox topMenu = new TopMenu().createTopMenu(primaryStage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 55.0);
        AnchorPane.setLeftAnchor(contentBox, 15.0);
        AnchorPane.setRightAnchor(contentBox, 15.0);
        AnchorPane.setBottomAnchor(contentBox, 15.0);

        Label titleLabel = new Label("Search Chats");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setPrefWidth(300);

        VBox.setMargin(titleLabel, new Insets(0, 0, 10, 0));
        VBox.setMargin(searchField, new Insets(0, 0, 10, 0));

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        VBox chatList = new VBox(10);
        chatList.setPadding(new Insets(5));
        scrollPane.setContent(chatList);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Runnable refreshList = () -> {
            chatList.getChildren().clear();

            String searchText = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
            ArrayList<Integer> allChats = DatabaseHandler.getAllChatIds();

            boolean foundAny = false;

            for (Integer chatId : allChats) {
                String chatName = DatabaseHandler.getChatName(chatId);
                boolean isPrivate = DatabaseHandler.isChatPrivate(chatId);

                if (isPrivate) {
                    continue;
                }

                if (!searchText.isEmpty() && !chatName.toLowerCase().contains(searchText)) {
                    continue;
                }

                HBox chatRow = createPublicChatRow(primaryStage, chatId);
                chatList.getChildren().add(chatRow);
                foundAny = true;
            }

            if (!foundAny) {
                Label noResult = new Label("No public chats found");
                noResult.setTextFill(Color.WHITE);
                chatList.getChildren().add(noResult);
            }
        };

        refreshList.run();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            refreshList.run();
        });

        contentBox.getChildren().addAll(titleLabel, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root);
    }

    private HBox createPublicChatRow(Stage primaryStage, Integer chatId) {
        HBox chatRow = new HBox(20);
        chatRow.setPadding(new Insets(5));
        chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");

        String chatPhotoUrl = DatabaseHandler.getChatPhoto(chatId);
        Circle chatPPCircle;

        try {
            ProfilePhoto photo = ProfilePhoto.fromString(chatPhotoUrl);
            chatPPCircle = photo.createCircle(20);
        } catch (Exception e) {
            chatPPCircle = new Circle(20, Color.LIGHTBLUE);
        }

        Label chatNameLbl = new Label(DatabaseHandler.getChatName(chatId));
        chatNameLbl.setPrefWidth(150);

        Label lastMessage = new Label("Public group");
        lastMessage.setStyle("-fx-font-size: 12px;");

        VBox chatInfoBox = new VBox(3, chatNameLbl, lastMessage);
        chatInfoBox.setPrefWidth(200);

        boolean alreadyJoined = DatabaseHandler.isUserInThisChat(chatId, App.currentUser.getUsername());

        Button joinGroupBtn = new Button(alreadyJoined ? "Open Chat" : "Join Group");
        Label memberCountLbl = new Label(DatabaseHandler.getMemeberCount(chatId) + " members");

        joinGroupBtn.setOnAction(e -> {
            if (!alreadyJoined) {
                boolean joined = DatabaseHandler.addUserToChat(chatId, App.currentUser.getUsername(), false);
                if (joined) {
                    App.currentChat = chatId;
                    primaryStage.setScene(new InsideChat(chatId, App.currentUser.getUsername()).createInsideChatScene(primaryStage));
                }
            } else {
                App.currentChat = chatId;
                primaryStage.setScene(new InsideChat(chatId, App.currentUser.getUsername()).createInsideChatScene(primaryStage));
            }
        });

        VBox joinBox = new VBox(3, joinGroupBtn, memberCountLbl);
        joinBox.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);

        chatRow.setOnMouseClicked((MouseEvent e) -> {
            App.currentChat = chatId;
            primaryStage.setScene(new InsideChat(chatId, App.currentUser.getUsername()).createInsideChatScene(primaryStage));
        });

        return chatRow;
    }
}