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
    public static Scene createSearchChatsScene(Stage primaryStage) {
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #0B0F1A;");
        HBox topMenu = TopMenu.createTopMenu(primaryStage);
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
        //the searching logic will be added, results will be displayed in boxes under the bar
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

         //choosing random chats for recommendation in search chat
         /*ArrayList<PublicChat> recommendedChats = new ArrayList<>();
        Random random = new Random();
         while (recommendedChats.size() < 10) {
         int index = random.nextInt(publicChats.size());
         PublicChat chat = publicChats.get(index);
         if (!recommendedChats.contains(chat)) {
         recommendedChats.add(chat);
         }
         }*/

        /*for (PublicChat chat : recommendedChats) {
        HBox chats = new HBox(10);
        chatRow.setPadding(new Insets(5));
        chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");*/
        //actual chat names will be replaced
        String ane[] = { "m", "a", "n", "i", "f", "e", "s", "t","a","tt" };
        for (String harf : ane) {
            HBox chatRow = new HBox(20);
            chatRow.setPadding(new Insets(5));
            chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");
            // chats' pp will be used here
            Circle chatPPCircle = new Circle(20, Color.LIGHTBLUE);
            Label harfs = new Label(harf); // replace with chatname
            harfs.setPrefWidth(150);
            Label lastMessage = new Label("last message");
            lastMessage.setStyle("-fx-font-size: 12px;");
            VBox chatInfoBox = new VBox(3, harfs, lastMessage);
            chatInfoBox.setPrefWidth(200);
            //when join group is clicked, the seleced chat should be added to mychats
            Button joinGroupBtn = new Button("Join Group");
            Label memberCountLbl = new Label("122 members");
            VBox joinBox = new VBox(3, joinGroupBtn, memberCountLbl);
            joinBox.setAlignment(Pos.CENTER_RIGHT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
            chatList.getChildren().add(chatRow);
        }

        contentBox.getChildren().addAll(titleLabel, searchField, scrollPane);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
