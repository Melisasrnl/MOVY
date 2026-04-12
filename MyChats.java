package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MyChats {

    public Scene createMyChatsScene(Stage stage) {
        AnchorPane root = new AnchorPane();

        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 55.0);
        AnchorPane.setLeftAnchor(contentBox, 15.0);
        AnchorPane.setRightAnchor(contentBox, 15.0);
        AnchorPane.setBottomAnchor(contentBox, 15.0);

        Label titleLabel = new Label("My Chats");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button createNewChatBtn = new Button("+");
        createNewChatBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        createNewChatBtn.setPrefSize(40, 40);
        createNewChatBtn.setTooltip(new Tooltip("Create New Group Chat"));

        createNewChatBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                stage.setScene(new CreateNewGroupChat().createNewGroupChatScene(stage));
            }
        });

        HBox titleLine = new HBox(10);
        titleLine.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        titleLine.getChildren().addAll(titleLabel, createNewChatBtn);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        VBox chatList = new VBox(10);
        chatList.setPadding(new Insets(5));
        scrollPane.setContent(chatList);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        ArrayList<Integer> chatIds = DatabaseHandler.getChats(App.currentUser.getUsername());

        Runnable refreshList = () -> {
            chatList.getChildren().clear();

            String searchText = searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
            boolean foundAny = false;

            for (Integer chatId : chatIds) {
                String chatName = DatabaseHandler.getChatName(chatId);

                if (searchText.isEmpty() || chatName.toLowerCase().contains(searchText)) {
                    HBox chatRow = createChatRow(stage, chatId);
                    chatList.getChildren().add(chatRow);
                    foundAny = true;
                }
            }

            if (!foundAny) {
                Label noChatsFound = new Label(
                    chatIds.isEmpty() ? "No chats are created yet" : "No matching chats found"
                );
                chatList.getChildren().add(noChatsFound);
            }
        };

        refreshList.run();

        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            refreshList.run();
        });

        contentBox.getChildren().addAll(titleLine, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root);
    }

    private HBox createChatRow(Stage stage, Integer chatId) {
        HBox chatRow = new HBox(20);
        chatRow.setPadding(new Insets(5));

        chatRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                App.currentChat = chatId;
                stage.setScene(new InsideChat(chatId, App.currentUser.getUsername()).createInsideChatScene(stage));
            }
        });

        String urlPP = DatabaseHandler.getChatPhoto(chatId);
        ProfilePhoto chatPP = ProfilePhoto.fromString(urlPP);
        Circle chatPPCircle = chatPP.createCircle(20);

        Label chatsName = new Label(DatabaseHandler.getChatName(chatId));
        chatsName.setPrefWidth(150);

        VBox chatInfoBox = new VBox(3, chatsName);
        chatInfoBox.setPrefWidth(200);

        Integer memberCount = DatabaseHandler.getMemeberCount(chatId);
        Label memberCountLbl = new Label(memberCount + " members");

        VBox joinBox = new VBox(3, memberCountLbl);
        joinBox.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
        return chatRow;
    }

    private static class EventHandler<T extends javafx.event.Event> implements javafx.event.EventHandler<T> {
        @Override
        public void handle(T event) {
        }
    }
}