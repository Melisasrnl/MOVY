import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
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

import java.util.List;

public class Followers {

    public Scene createFollowersScene(Stage stage /*List<User> followersList*/) {
        HBox topMenu = new TopMenu().createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);
        VBox root = new VBox(10);
        root.setPrefWidth(400);
        root.getChildren().add(topMenu);

        Label title = new Label("Followers");
        title.setStyle("-fx-font-size: 35px;");

        root.getChildren().add(title);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        root.getChildren().add(searchField);
        VBox listVBox = new VBox(10);
        /*for (User user : followingList) {
            HBox userRow = new HBox(10);
            // Profile photos will be added
            profilePic.setFitWidth(40);
            profilePic.setFitHeight(40);

            VBox userInfo = new VBox(5);
            Label usernameLabel = new Label(user.getUsername());
            Label followerCountLabel = new Label(user.getFollowers().size() + " followers");
            userInfo.getChildren().addAll(usernameLabel, followerCountLabel);

            // Buttons
            Button unfollowBtn = new Button("Unfollow");
            //if friends:
            Button chatBtn = new Button("Chat");

            HBox buttons = new HBox(5, unfollowBtn, chatBtn);

            HBox.setHgrow(userInfo, Priority.ALWAYS);

            userRow.getChildren().addAll(profilePic, userInfo, buttons);
            listVBox.getChildren().add(userRow);
        }*/
       //a temporary array is created to visualize the components
        String[] followinglist = {"q","w","e","r","t","y"};
        for (String person : followinglist) {
            HBox userRow = new HBox(10);
            // Profile photos will be added
            Circle profilePic = new Circle(25);
            profilePic.setFill(Color.CYAN);


            VBox userInfo = new VBox(5);
            Label usernameLabel = new Label(person);
            Label followerCountLabel = new Label( "123 followers");
            userInfo.getChildren().addAll(usernameLabel, followerCountLabel);

            //Only one of these two buttons will be shown
            //If not friends:
            Button unfollowBtn = new Button("Follow Back");
            unfollowBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //follow back, change the button to chat
            }
        });
            //if friends:
            Button chatBtn = new Button("Chat");
            chatBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //new scene will be added
            }
        });
            HBox buttons = new HBox(5, unfollowBtn, chatBtn);
            HBox.setHgrow(userInfo, Priority.ALWAYS);
            userRow.getChildren().addAll(profilePic, userInfo, buttons);
            listVBox.getChildren().add(userRow);
        }

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(listVBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        return new Scene(root);
    }
}