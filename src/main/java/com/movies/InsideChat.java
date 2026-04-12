package com.movies;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class InsideChat {
    private Label nameLabel;
    private Label privacyLabel;
    private Label countLabel;
    private VBox messageBox;
    private ScrollPane scrollPane;
    private TextField inputField;
    private Button sendButton;
    private Button info;
    private Scene mainScene;

    private Integer chatId;
    private String currentUser;
    private Stage stage;

    public InsideChat(Integer chatId, String currentUser) {
        this.chatId = chatId;
        this.currentUser = currentUser;
    }

    private HBox createHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(12);
        header.setStyle("-fx-background-color: #2c2727;");
        header.setPadding(new Insets(10, 14, 10, 14));

        Button backButton = new Button("");
        backButton.setShape(new javafx.scene.shape.Polygon(0, 12, 24, 0, 24, 24));
        backButton.setStyle("-fx-background-color: #b6b0b0c9;");
        backButton.setOnAction(e -> {
            stage.setScene(new MyChats().createMyChatsScene(stage));
        });

        Circle avatar = new Circle(20);
        avatar.setFill(Color.GRAY);

        VBox first = new VBox();
        first.setAlignment(Pos.CENTER);

        nameLabel = new Label(DatabaseHandler.getChatName(chatId));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(20));

        privacyLabel = new Label(DatabaseHandler.isChatPrivate(chatId) ? "Private Chat" : "Public Chat");
        privacyLabel.setTextFill(Color.WHITE);
        privacyLabel.setFont(new Font(10));

        first.getChildren().addAll(nameLabel, privacyLabel);

        VBox second = new VBox();
        second.setAlignment(Pos.CENTER);

        info = new Button("Group Info");

        countLabel = new Label(DatabaseHandler.getMemeberCount(chatId) + " Members");
        countLabel.setTextFill(Color.WHITE);

        second.getChildren().addAll(info, countLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(backButton, avatar, first, spacer, second);

        return header;
    }

    private VBox createMessageBox() {
        messageBox = new VBox();
        messageBox.setStyle("-fx-background-color: #544c4c;");
        messageBox.setSpacing(10);
        return messageBox;
    }

    public void addMessage(String senderName, String messageText, boolean isMine) {
        if (messageText == null || messageText.trim().equals("")) {
            return;
        }

        HBox aBubble = new HBox();
        aBubble.setSpacing(5);

        VBox who = new VBox();
        who.setAlignment(Pos.CENTER);

        Circle photo = new Circle();
        photo.setRadius(20);
        photo.setFill(Color.GRAY);

        Label sender = new Label(senderName);
        sender.setFont(new Font(10));
        sender.setTextFill(Color.WHITE);

        who.getChildren().addAll(photo, sender);

        Label text = new Label(messageText);
        text.setFont(new Font(20));
        text.setTextFill(Color.BLACK);
        text.setPadding(new Insets(5));
        text.setWrapText(true);
        text.setMaxWidth(300);

        if (isMine) {
            text.setStyle("-fx-background-radius: 15; -fx-background-color: #c6f6c6;");
        } else {
            text.setStyle("-fx-background-radius: 15; -fx-background-color: #d2cbcb;");
        }

        aBubble.getChildren().addAll(who, text);

        HBox bubbles = new HBox();
        bubbles.setPadding(new Insets(5));

        if (isMine) {
            bubbles.setAlignment(Pos.TOP_RIGHT);
            bubbles.getChildren().addAll(aBubble);
        } else {
            bubbles.setAlignment(Pos.TOP_LEFT);
            bubbles.getChildren().addAll(aBubble);
        }

        messageBox.getChildren().add(bubbles);

        if (scrollPane != null) {
            scrollPane.layout();
            scrollPane.setVvalue(1.0);
        }
    }

    private void refreshHeaderInfo() {
        nameLabel.setText(DatabaseHandler.getChatName(chatId));
        privacyLabel.setText(DatabaseHandler.isChatPrivate(chatId) ? "Private Chat" : "Public Chat");
        countLabel.setText(DatabaseHandler.getMemeberCount(chatId) + " Members");
    }

    public HBox createSending() {
        HBox sendBox = new HBox(10);
        sendBox.setAlignment(Pos.CENTER);

        inputField = new TextField();
        inputField.setPromptText("Type...");
        inputField.setPrefWidth(400);
        inputField.setFont(new Font(20));

        sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #b5a9a9;");

        sendBox.getChildren().addAll(inputField, sendButton);

        return sendBox;
    }

    private void loadOldMessages() {
        messageBox.getChildren().clear();

        ArrayList<String[]> messages = DatabaseHandler.getMessages(chatId);

        for (String[] msg : messages) {
            String senderName = msg[0];
            String messageText = msg[1];
            boolean isMine = senderName.equals(currentUser);

            addMessage(senderName, messageText, isMine);
        }
    }

    public Scene createInsideChatScene(Stage stage) {
        this.stage = stage;

        HBox topMenu = new TopMenu().createTopMenu(stage);
        HBox header = createHeader();
        VBox messages = createMessageBox();
        HBox send = createSending();

        scrollPane = new ScrollPane(messages);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-control-inner-background: #544c4c;");

        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #544c4c;");
        root.getChildren().addAll(topMenu, header, scrollPane, send);

        refreshHeaderInfo();
        loadOldMessages();

        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String messageText = inputField.getText();

                if (messageText == null || messageText.trim().equals("")) {
                    return;
                }

                boolean success = DatabaseHandler.newMessage(chatId, currentUser, messageText.trim());

                if (success) {
                    addMessage(currentUser, messageText.trim(), true);
                    inputField.setText("");
                    refreshHeaderInfo();
                }
            }
        });

        inputField.setOnAction(e -> sendButton.fire());

        mainScene = new Scene(root, 800, 600);

        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GroupInfo groupPage = new GroupInfo(stage, mainScene, chatId, currentUser);
                Scene groupScene = groupPage.createGroupInfoScene();
                stage.setScene(groupScene);
            }
        });

        return mainScene;
    }
}