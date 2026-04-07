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
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import java.util.List;

public class MemberSelectionGroupChat {
    public Scene createMemberSelectionGroupChatscene(Stage stage, int chatId) {
        AnchorPane root = new AnchorPane();
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        AnchorPane contentBox = new AnchorPane();
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 55.0);
        AnchorPane.setLeftAnchor(contentBox, 15.0);
        AnchorPane.setRightAnchor(contentBox, 15.0);
        AnchorPane.setBottomAnchor(contentBox, 15.0);

        TextField searchField = new TextField();
        searchField.setPromptText("Search for Members...");
        VBox searchResult = new VBox();

        String username = searchField.getText();

        AnchorPane.setTopAnchor(searchField, 100.0);
        AnchorPane.setLeftAnchor(searchField, 10.0);
        AnchorPane.setRightAnchor(searchField, 10.0);
        searchField.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                String username = searchField.getText();
                searchResult.getChildren().clear();
                if (!username.isEmpty()) {
                    String foundUsername = DatabaseHandler.userStringGetter("username", "username", username);
                    if (!foundUsername.equals("userstringnotfound")) { // if found
                        HBox userRow = new HBox(10);
                        userRow.setPadding(new Insets(5));
                        userRow.isAdded(false);
                        ProfilePhoto userPP = Main.currentUser.getProfilePhoto();
                        Circle userPPCircle = userPP.ProfilePhoto().createCircle(18);
                        Label usernameLbl = new Label(foundUsername);
                        usernameLbl.setStyle("-fx-font-size: 14px;");
                        Label isAddedLbl = new Label("+");
                        Region spacer = new Region();
                        HBox.setHgrow(spacer, Priority.ALWAYS);
                        userRow.getChildren().addAll(userPPCircle, usernameLbl, spacer, isAddedLbl);
                        userRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            public void handle(MouseEvent e) {
                                boolean isAdded = (Boolean) userRow.isAdded();
                                if (!isAdded) {
                                    boolean add = DatabaseHandler.addUserToChat(chatId, foundUsername, false);
                                    if (add) {
                                        userRow.setUserData(true);
                                        isAddedLbl.setText("-");
                                    }
                                } else {
                                    userRow.setUserData(false);
                                    isAddedLbl.setText("+");
                                }
                            }
                        });
                        searchResult.getChildren().add(userRow);
                    } else {
                        Label notFound = new Label("There is no such user");
                        searchResult.getChildren().add(notFound);
                    }
                }
            }
        });
        VBox chatList = new VBox(10);
        chatList.setPadding(new Insets(5));

        ScrollPane scrollPane = new ScrollPane(chatList);
        scrollPane.setFitToWidth(true);
        AnchorPane.setTopAnchor(scrollPane, 130.0);
        AnchorPane.setLeftAnchor(scrollPane, 10.0);
        AnchorPane.setRightAnchor(scrollPane, 10.0);
        AnchorPane.setBottomAnchor(scrollPane, 40.0);

        Button createGroup = new Button("Create Chat");
        AnchorPane.setRightAnchor(createGroup, 20.0);
        AnchorPane.setBottomAnchor(createGroup, 0.0);
        List<HBox> allChatRows = new ArrayList<>();

        // followers and followings will be recommended automatically
        ArrayList<String> followingList = DatabaseHandler.getFollowings(Main.currentUser.getUsername());
        ArrayList<String> followersList = DatabaseHandler.getFollowers(Main.currentUser.getUsername());
        ArrayList<String> allList = new ArrayList<>(followingList);
        for (String aUser : followersList) {
            if (!allList.contains(aUser)) {
                allList.add(aUser);
            }
        }
        for (String recomMember : allList) {
            HBox chatRow = new HBox(20);
            chatRow.setPadding(new Insets(5));
            chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");
            ProfilePhoto userPP = Main.currentUser.getProfilePhoto();
            Circle userPPCircle = userPP.ProfilePhoto().createCircle(18);
            Label recomName = new Label(recomMember);
            recomName.setPrefWidth(150);
            final boolean[] isPlusSign = { true };
            VBox chatInfoBox = new VBox(3, recomName);
            chatInfoBox.setPrefWidth(200);
            Label isAlreadyAdded = new Label("+");
            VBox joinBox = new VBox(3, isAlreadyAdded);
            joinBox.setAlignment(Pos.CENTER_RIGHT);
            String thisHarf = recomMember;
            chatRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    if (isPlusSign[0]) {
                        isAlreadyAdded.setText("-");
                    } else {
                        isAlreadyAdded.setText("+");
                    }
                    isPlusSign[0] = !isPlusSign[0];
                }
            });
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            chatRow.getChildren().addAll(userPPCircle, chatInfoBox, spacer, joinBox);
            chatList.getChildren().add(chatRow);
            allChatRows.add(chatRow);
        }

        createGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                for (HBox row : allChatRows) {
                    Label toggleLabel = (Label) ((VBox) row.getChildren().get(3)).getChildren().get(0);
                    Label chatNameLabel = (Label) ((VBox) row.getChildren().get(1)).getChildren().get(0);
                    if ("-".equals(toggleLabel.getText())) {
                        String chatName = chatNameLabel.getText();
                        System.out.println(chatName + " added");
                        //DatabaseHandler.createNewChat()
                        stage.setScene(new MyChats().createMyChatsScene(stage));

                        // add the members,create the chat, add the chat to mychats page
                    }
                }
            }
        });

        contentBox.getChildren().addAll(searchField, scrollPane, chatList, createGroup);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root);
    }
}
