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

    public static Scene createMyChatsScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        HBox topMenu = TopMenu.createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 8.0);
        AnchorPane.setLeftAnchor(topMenu, 4.0);
        AnchorPane.setRightAnchor(topMenu, 4.0);

        VBox contentBox = new VBox(10);
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 54.0);
        AnchorPane.setLeftAnchor(contentBox, 16.0);
        AnchorPane.setRightAnchor(contentBox, 16.0);
        AnchorPane.setBottomAnchor(contentBox, 16.0);

        Label titleLabel = new Label("My Chats");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        Button createNewChatBtn = new Button("+");
        createNewChatBtn.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        createNewChatBtn.setPrefSize(40, 40);
        createNewChatBtn.setTooltip(new Tooltip("Create New Group Chat"));
        HBox titleLine = new HBox(10);
        titleLine.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        titleLine.getChildren().addAll(titleLabel, createNewChatBtn);
        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.setPrefWidth(300);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        VBox chatList = new VBox(10);
        chatList.setPadding(new Insets(5));
        scrollPane.setContent(chatList);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // mychats will be replaced with that
        String ane[] = { "m", "a", "n", "i", "f", "e", "s", "t", "a", "tt" };
        for (String harf : ane) {
            HBox chatRow = new HBox(20);
            chatRow.setPadding(new Insets(5));
            chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");
            chatRow.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent e) {
                    //bensu's page will be called
                }
            });
            // chats' pp will be used here
            Circle chatPPCircle = new Circle(20, Color.LIGHTBLUE);
            Label harfs = new Label(harf); // replace with chatname
            harfs.setPrefWidth(150);
            Label lastMessage = new Label("last message");
            lastMessage.setStyle("-fx-font-size: 12px;");
            VBox chatInfoBox = new VBox(3, harfs, lastMessage);
            chatInfoBox.setPrefWidth(200);
            //member count will be corrected later on
            Label memberCountLbl = new Label("122 members");
            VBox joinBox = new VBox(3, memberCountLbl);
            joinBox.setAlignment(Pos.CENTER_RIGHT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
            chatList.getChildren().add(chatRow);
        }

        contentBox.getChildren().addAll(titleLine, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
