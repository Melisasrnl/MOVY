import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
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
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

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
        searchField.setPrefWidth(300);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        VBox chatList = new VBox(10);
        chatList.setPadding(new Insets(5));
        scrollPane.setContent(chatList);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        createNewChatBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                stage.setScene(new CreateNewGroupChat().createNewGroupChatScene(stage));
            }
        });
        ArrayList<Integer> chatIds = DatabaseHandler.getChats(Main.currentUser.getUsername());
        HBox titleLine = new HBox(10);
        titleLine.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        titleLine.getChildren().addAll(titleLabel, createNewChatBtn);
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        String searchResult = searchField.getText();
        for (Integer chat : chatIds) {
            String chatName = DatabaseHandler.getChatName(chat);
            if (chatName.equals(searchResult)) {
                HBox box = new HBox();
                ProfilePhoto chatPP = ProfilePhoto.fromString(DatabaseHandler.getChatPhoto(chat));
                Circle chatPPCircle = chatPP.createCircle(20);
                Label chatNameLbl = new Label(chatName);
                box.getChildren().addAll(chatPPCircle, chatNameLbl);
                box.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        //choosing the chat
                        Main.currentChat = chat;
                        stage.setScene(new InsideChat(chat).createInsideChatScene(stage));
                    }
                });
                chatList.getChildren().add(box);
            }
        }

        
            if (chatIds.isEmpty()) {
                Label noChatsFound = new Label("No chats are created yet");
                chatList.getChildren().add(noChatsFound);
            }
            else {
            
                for (int chat : chatIds) {
                HBox chatRow = new HBox(20);
                chatRow.setPadding(new Insets(5));
                String chatName = DatabaseHandler.getChatName(chat);
                chatRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        //do not forget to add 
                        //  public static Integer currentChat= null;
                        //to the main
                        Main.currentChat = chat;
                        stage.setScene(new InsideChat(chat).createInsideChatScene(stage));
                    }
                });
                String urlPP = DatabaseHandler.getChatPhoto(chat);
                ProfilePhoto chatPP = ProfilePhoto.fromString(urlPP);
                Circle chatPPCircle = chatPP.createCircle(20);
                Label chatsName = new Label(chatsName);
                chatsName.setPrefWidth(150);
                VBox chatInfoBox = new VBox(3, chatsName);
                chatInfoBox.setPrefWidth(200);
                Integer memberCount = DatabaseHandler.getMemeberCount(chat);
                Label memberCountLbl = new Label(memberCount);
                VBox joinBox = new VBox(3, memberCountLbl);
                joinBox.setAlignment(Pos.CENTER_RIGHT);
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);
                chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
                chatList.getChildren().add(chatRow);
            }
        }

        contentBox.getChildren().addAll(titleLine, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
