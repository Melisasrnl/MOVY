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
import javafx.scene.control.*;
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

public class ProfilePage {
    public static Scene createProfilePageScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        HBox topMenu = TopMenu.createTopMenu(stage);
        AnchorPane.setTopAnchor(topMenu, 10.0);
        AnchorPane.setLeftAnchor(topMenu, 5.0);
        AnchorPane.setRightAnchor(topMenu, 5.0);

        AnchorPane contentBox = new AnchorPane();
        contentBox.setPadding(new Insets(20));
        AnchorPane.setTopAnchor(contentBox, 40.0);
        AnchorPane.setLeftAnchor(contentBox, 0.0);
        AnchorPane.setRightAnchor(contentBox, 0.0);
        AnchorPane.setBottomAnchor(contentBox, 0.0);

        Button returnToHomePage = new Button("<");
        AnchorPane.setTopAnchor(returnToHomePage, 0.0);
        AnchorPane.setLeftAnchor(returnToHomePage, -15.0);
        Circle userPPCircle = new Circle(27, Color.DODGERBLUE);
        Button edit = new Button("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                stage.setScene(EditProfile.createEditProfileScene(stage));
            }
        });
        Label username = new Label("ceren");
        Label bio = new Label("your average romcom enjoyer");
        VBox userInfo = new VBox(5, username, bio, edit);
        Button followers = new Button("15 \n Followers");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // a new scene will be added
            }
        });
        Button followings = new Button("13 \n Followings");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                // a new scene will be added
            }
        });
        HBox aboutFollows = new HBox(20, followers, followings);
        Button logout = new Button("Logout");
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("LOGOUT");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                Label label = new Label("Do you want to log out?");
                label.setWrapText(true);
                label.setMaxWidth(250);
                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                    }
                });
                Button logoutBtn = new Button("Logout");
                logoutBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                        // a new scene will be added
                    }
                });
            }
        });
        VBox rightSideBio = new VBox(20, aboutFollows, logout);
        HBox topRow = new HBox(20, userPPCircle, userInfo);
        topRow.setPadding(new Insets(20, 20, 20, 15));
        topRow.setAlignment(Pos.TOP_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(spacer, rightSideBio);

        Label favorites = new Label("Favorites");
        Button seeAllFavs = new Button("See All");

        Region spacerFav = new Region();
        HBox.setHgrow(spacerFav, Priority.ALWAYS);
        HBox favsLine = new HBox(10,favorites,spacerFav, seeAllFavs);
        favsLine.setAlignment(Pos.CENTER_LEFT);
        favsLine.setMaxWidth(Double.MAX_VALUE);

        Label recentWatches = new Label("Recent Watches");
        Button seeAllRWs = new Button("See All");
        Region spacerRWs = new Region();
        HBox.setHgrow(spacerRWs, Priority.ALWAYS);


        HBox RWsLine = new HBox(10,recentWatches,spacerRWs,seeAllRWs);
        RWsLine.setAlignment(Pos.CENTER_LEFT);
        RWsLine.setMaxWidth(Double.MAX_VALUE);

        VBox bottomRow = new VBox(120, favsLine,RWsLine);
        bottomRow.setPadding(new Insets(20, 20, 20, 15));
        bottomRow.setAlignment(Pos.CENTER_LEFT);

        VBox leftSide = new VBox(20, topRow, bottomRow);
        leftSide.setPadding(new Insets(20, 20, 20, 15));
        leftSide.setAlignment(Pos.TOP_LEFT);
        Region spacerTwo = new Region();
        VBox.setVgrow(spacerTwo, Priority.ALWAYS);

        AnchorPane.setTopAnchor(leftSide, 0.0);
        AnchorPane.setLeftAnchor(leftSide, 0.0);
        AnchorPane.setRightAnchor(leftSide, 300.0);


        contentBox.getChildren().addAll(returnToHomePage, leftSide);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);
    }
}
