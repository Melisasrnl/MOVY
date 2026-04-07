
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

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

        // choosing 10 (if possible) random chats for recommendation in search chat
        ArrayList<Integer> userChatIds = DatabaseHandler.getChats(Main.currentUser.getUsername());
        ArrayList<Integer> allchats = DatabaseHandler.getAllChats();

        ArrayList<Integer> recommendedChats = new ArrayList<>(allchats);
        recommendedChats.removeAll(userChatIds);
        Collections.shuffle(recommendedChats);
        int limit = Math.min(10, recommendedChats.size());
        for (int i = 0; i < limit; i++) {
            int chatId = recommendedChats.get(i);
            String chatName = DatabaseHandler.getChatName(chatId);
            HBox chatRow = new HBox(20);
            chatRow.setPadding(new Insets(5));

            String photoUrl = DatabaseHandler.getChatPhoto(chatId); 
            ProfilePhoto pp = ProfilePhoto.fromString(photoUrl);
            Circle chatPPCircle = pp.createCircle(18);
            Label chatNameLbl = new Label(chatName);
            chatNameLbl.setPrefWidth(150);
            VBox chatInfoBox = new VBox(3, chatNameLbl);
            chatInfoBox.setPrefWidth(200);

            VBox joinBox = new VBox(3);
            joinBox.setAlignment(Pos.CENTER_RIGHT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button joinGroupBtn = new Button("Join Group");
            Label memberCountLbl = new Label(DatabaseHandler.getMemeberCount(chatId) + " members");
            joinBox.getChildren().addAll(joinGroupBtn, memberCountLbl);

            joinGroupBtn.setOnAction(e -> {
                if (DatabaseHandler.addUserToChat(chatId, Main.currentUser.getUsername(), false)) {
                    joinGroupBtn.setText("Joined!!");
                    joinGroupBtn.setDisable(true);
                    userChatIds.add(chatId);
                }
            });




        contentBox.getChildren().addAll(titleLabel, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);
        searchField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                chatList.getChildren().clear();
                String searchText = searchField.getText().trim();

                if (!searchText.isEmpty()) {
                    for (Integer chatId : allchats) {
                        String chatName = DatabaseHandler.getChatName(chatId);

                        if (chatName.contains(searchText)) {
                            HBox chatRow = new HBox(20);
                            chatRow.setPadding(new Insets(5));

                            String photoUrl = DatabaseHandler.getChatPhoto(chatId);
                            ProfilePhoto pp = ProfilePhoto.fromString(photoUrl);
                            Circle chatPPCircle = pp.createCircle(18);
                            Label chatNameLbl = new Label(chatName);
                            chatNameLbl.setPrefWidth(150);
                            VBox chatInfoBox = new VBox(3, chatNameLbl);
                            chatInfoBox.setPrefWidth(200);

                            VBox joinBox = new VBox(3);
                            joinBox.setAlignment(Pos.CENTER_RIGHT);
                            Region spacer = new Region();
                            HBox.setHgrow(spacer, Priority.ALWAYS);

                            if (!userChatIds.contains(chatId)) {
                                Button joinGroupBtn = new Button("Join Group");
                                Label memberCountLbl = new Label(DatabaseHandler.getMemeberCount(chatId) + " members");
                                joinBox.getChildren().addAll(joinGroupBtn, memberCountLbl);

                                joinGroupBtn.setOnAction(new EventHandler<ActionEvent>() {

                                    public void handle(ActionEvent e) {
                                        if (DatabaseHandler.addUserToChat(chatId, Main.currentUser.getUsername(), false)&&!DatabaseHandler.isChatPrivate(chatId)) {
                                            joinGroupBtn.setText("Joined!!");
                                            joinGroupBtn.setDisable(true);
                                            userChatIds.add(chatId);
                                        } else {
                                            joinGroupBtn.setText("Impossible!!");
                                            joinGroupBtn.setDisable(true);
                                        }
                                    }
                                });
                            } else {
                                Label memberCountLbl = new Label(DatabaseHandler.getMemeberCount(chatId) + " members");
                                joinBox.getChildren().add(memberCountLbl);
                            }

                            chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
                            chatList.getChildren().add(chatRow);
                        }
                    }
                }
            }
        });

        return new Scene(root);
    }
}
