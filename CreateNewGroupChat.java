import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

import com.movies.DatabaseHandler;

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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class CreateNewGroupChat {
    public  Scene createNewGroupChatScene(Stage stage) {
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
        Label newGroupChatLbl = new Label("New Group Chat");

        AnchorPane.setTopAnchor(newGroupChatLbl, 20.0);
        AnchorPane.setLeftAnchor(newGroupChatLbl, 20.0);
        newGroupChatLbl.setStyle("-fx-font-size: 30px;"); 
        TextField chatName = new TextField();
        chatName.setPromptText("Write Group Name...");
        chatName.setOnAction(e -> {
        String groupName = chatName.getText();
    });


        AnchorPane.setTopAnchor(chatName, 70.0);
        AnchorPane.setLeftAnchor(chatName, 20.0);
        AnchorPane.setRightAnchor(chatName, 20.0);

        ToggleGroup publicOrPriv = new ToggleGroup();
        ToggleButton publicBtn = new ToggleButton("Public");
        AnchorPane.setTopAnchor(publicBtn, 120.0);
        AnchorPane.setLeftAnchor(publicBtn, 20.0);
        publicBtn.setToggleGroup(publicOrPriv);
        publicBtn.setSelected(true);
        boolean isPublic = true;
        publicBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                isPublic = true;
            }
        });
        ToggleButton privateBtn = new ToggleButton("Private");
        AnchorPane.setTopAnchor(privateBtn, 120.0);
        AnchorPane.setLeftAnchor(privateBtn, 120.0);
        privateBtn.setToggleGroup(publicOrPriv);

        privateBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                isPublic = false;
            }
        });
        isPublic=!isPublic;
        Label choosePPLabel = new Label("Choose a Profile Photo:");
        choosePPLabel.setStyle("-fx-font-size: 20px;");
        AnchorPane.setTopAnchor(choosePPLabel, 160.0);
        AnchorPane.setLeftAnchor(choosePPLabel, 20.0);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        int col = 0, row = 0;
        ArrayList<Button> buttons = new ArrayList<Button>();
        for (ProfilePhoto photo : ProfilePhoto.values()) {
            ImageView img = new ImageView(new Image(photo.getUrl()));
            img.setFitWidth(50);
            img.setFitHeight(50);
            Button btn = new Button();
            btn.setGraphic(img);
            buttons.add(btn);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    //idkkkkk
                    //DatabaseHandler.setChatPhoto()
                    for (Button b : buttons) {
                        b.setDisable(true);
                    }
                }
            });

            grid.add(btn, col, row);

            col++;
            if (col == 4) {
                col = 0;
                row++;
            }
        }
        HBox colorIconsForPP = new HBox(10);
        AnchorPane.setTopAnchor(colorIconsForPP, 200.0);
        AnchorPane.setLeftAnchor(colorIconsForPP, 20.0);
        HBox otherPPOptions = new HBox(10);
        AnchorPane.setTopAnchor(otherPPOptions, 240.0);
        AnchorPane.setLeftAnchor(otherPPOptions, 20.0);
        Button searchMembers = new Button("Search to Add Members...");
        AnchorPane.setLeftAnchor(searchMembers, 20.0);
        AnchorPane.setRightAnchor(searchMembers, 20.0);
        AnchorPane.setTopAnchor(searchMembers, 400.0);
        searchMembers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                ArrayList<String> members = new ArrayList<>();
                ArrayList<Boolean> roles = new ArrayList<>();
                members.add(Main.currentUser.getUsername());
                roles.add(true);
                DatabaseHandler.createNewChat(groupName, members, roles, isPublic);
                stage.setScene(new MemberSelectionGroupChat().createMemberSelectionGroupChatscene(stage));
            }
        });
        contentBox.getChildren().addAll(newGroupChatLbl, chatName, publicBtn, privateBtn, choosePPLabel, colorIconsForPP,
                otherPPOptions, searchMembers);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
