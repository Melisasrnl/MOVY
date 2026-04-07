package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GroupInfo {

    private Label nameLabel;
    private Label privacyLabel;
    private Label countLabel;
    private VBox memList;
    private Button addMemberButton;
    private Button deleteGroup;
    private StackPane root;
    private Stage stage;
    private Scene previousScene;

    private Integer chatId;
    private String currentUser;

    public GroupInfo(Stage stage, Scene previousScene, Integer chatId, String currentUser) {
        this.stage = stage;
        this.previousScene = previousScene;
        this.chatId = chatId;
        this.currentUser = currentUser;
    }

    public Scene createGroupInfoScene() {
        HBox topMenu = new TopMenu().createTopMenu(this.stage);
        HBox header = createInfoHeader();
        VBox members = createMemberBox();

        VBox mainContent = new VBox();
        mainContent.getChildren().addAll(topMenu, header, members);

        this.root = new StackPane();
        this.root.getChildren().add(mainContent);

        return new Scene(this.root, 800, 600);
    }

    public HBox createInfoHeader() {
        HBox header = new HBox(5);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(12);
        header.setStyle("-fx-background-color: #2c2727;");
        header.setPadding(new Insets(10, 14, 10, 14));

        Button backButton = new Button("");
        backButton.setShape(new javafx.scene.shape.Polygon(0,12,24,0,24,24));
        backButton.setStyle("-fx-background-color: #b6b0b0c9;");
        backButton.setOnAction(e -> stage.setScene(previousScene));

        Circle avatar = new Circle(20);
        avatar.setFill(Color.GRAY);

        VBox first = new VBox();
        first.setAlignment(Pos.CENTER);

        nameLabel = new Label(DatabaseHandler.getChatName(chatId));
        nameLabel.setTextFill(Color.WHITE);
        nameLabel.setFont(new Font(20));

        privacyLabel = new Label(DatabaseHandler.isChatPrivate(chatId) ? "Private Chat" : "Public Chat");
        privacyLabel.setTextFill(Color.WHITE);

        countLabel = new Label(DatabaseHandler.getMemeberCount(chatId) + " Members");
        countLabel.setTextFill(Color.WHITE);

        first.getChildren().addAll(nameLabel, privacyLabel, countLabel);

        Label groupInfo = new Label("   Group Info   ");
        groupInfo.setTextFill(Color.WHITE);
        groupInfo.setStyle("-fx-background-color: #000000; -fx-background-radius: 15;");

        boolean isAdmin = DatabaseHandler.isAdmin(chatId, currentUser);

        addMemberButton = new Button("Add Member");
        addMemberButton.setDisable(!isAdmin);
        addMemberButton.setOnAction(e -> showAddMemberPopup());

        deleteGroup = new Button("Delete Group");
        deleteGroup.setDisable(!isAdmin);
        deleteGroup.setOnAction(e -> {
            if (DatabaseHandler.isAdmin(chatId, currentUser)) {
                if (DatabaseHandler.deleteChat(chatId)) {
                    stage.setScene(previousScene);
                }
            }
        });

        Region spacer = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        header.getChildren().addAll(backButton, avatar, first, spacer, groupInfo, spacer2, addMemberButton, deleteGroup);
        return header;
    }

    public VBox createMemberBox() {
        memList = new VBox(10);
        memList.setPadding(new Insets(5));
        memList.setStyle("-fx-background-color: #ffffff;");
        refreshMembers();
        return memList;
    }

    public void createMemberRow(String member, boolean isAdminMember) {
        HBox row = new HBox(10);

        Circle image = new Circle(20);
        image.setFill(Color.LIGHTGRAY);

        Label name = new Label(isAdminMember ? member + " (admin)" : member);
        name.setFont(new Font(20));

        Button infoButton = new Button("i");
        infoButton.setShape(new Circle(10));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ContextMenu menu = new ContextMenu();

        MenuItem makeAdmin = new MenuItem("Make Admin");
        MenuItem removeAdmin = new MenuItem("Remove Admin");
        MenuItem removeMember = new MenuItem("Remove Member");

        boolean isAdmin = DatabaseHandler.isAdmin(chatId, currentUser);

        makeAdmin.setDisable(!isAdmin || isAdminMember);
        removeAdmin.setDisable(!isAdmin || !isAdminMember);
        removeMember.setDisable(!isAdmin || member.equals(currentUser));

        makeAdmin.setOnAction(e -> {
            if (DatabaseHandler.isAdmin(chatId, currentUser)) {
                DatabaseHandler.setAdmin(chatId, true, member);
                refreshMembers();
            }
        });

        removeAdmin.setOnAction(e -> {
            if (DatabaseHandler.isAdmin(chatId, currentUser)) {
                DatabaseHandler.setAdmin(chatId, false, member);
                refreshMembers();
            }
        });

        removeMember.setOnAction(e -> {
            if (DatabaseHandler.isAdmin(chatId, currentUser)) {
                DatabaseHandler.removeUserFromChat(chatId, member);
                refreshMembers();
            }
        });

        menu.getItems().addAll(makeAdmin, removeAdmin, removeMember);

        infoButton.setOnAction(e -> {
            if (isAdmin) {
                menu.show(infoButton, Side.BOTTOM, 0, 0);
            }
        });

        row.getChildren().addAll(image, name, spacer, infoButton);
        memList.getChildren().add(row);
    }

    private void showAddMemberPopup() {
        VBox popup = new VBox(15);
        popup.setPadding(new Insets(20));
        popup.setAlignment(Pos.CENTER);
        popup.setStyle("-fx-background-color: #2c2727; -fx-background-radius: 10;");

        Label title = new Label("Add Member");
        title.setTextFill(Color.WHITE);

        TextField input = new TextField();
        input.setPromptText("Enter name");

        Button add = new Button("Add");
        add.setOnAction(e -> {
            String name = input.getText();

            if (name != null && !name.isBlank()) {
                if (DatabaseHandler.isAdmin(chatId, currentUser) && !DatabaseHandler.isUserInThisChat(chatId, name)) {
                    DatabaseHandler.addUserToChat(chatId, name, false);
                    refreshMembers();
                    root.getChildren().remove(popup);
                }
            }
        });

        Button cancel = new Button("Cancel");
        cancel.setOnAction(e -> root.getChildren().remove(popup));

        HBox buttons = new HBox(10, add, cancel);
        buttons.setAlignment(Pos.CENTER);

        popup.getChildren().addAll(title, input, buttons);
        StackPane.setAlignment(popup, Pos.CENTER);
        root.getChildren().add(popup);
    }

    private void refreshMembers() {
        memList.getChildren().clear();

        ArrayList<String> users = DatabaseHandler.getUserList(chatId);

        for (String user : users) {
            boolean isAdminMember = DatabaseHandler.isAdmin(chatId, user);
            createMemberRow(user, isAdminMember);
        }

        countLabel.setText(DatabaseHandler.getMemeberCount(chatId) + " Members");
    }
}