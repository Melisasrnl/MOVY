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
        VBox userInfo = new VBox(5, username, bio,edit);
        Button followers = new Button("15 \n Followers");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //a new scene will be added
            }
        });
        Button followings = new Button("13 \n Followings");

        followers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //a new scene will be added
            }
        });
        HBox topRow = new HBox(20,userPPCircle,userInfo);
        topRow.setPadding(new Insets(20,20,20,15));
        topRow.setAlignment(Pos.TOP_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topRow.getChildren().addAll(spacer,followers,followings);

        AnchorPane.setTopAnchor(topRow,0.0);
        AnchorPane.setLeftAnchor(topRow,0.0);
        AnchorPane.setRightAnchor(topRow,300.0);

        contentBox.getChildren().addAll(returnToHomePage,topRow);
        root.getChildren().addAll(topMenu,contentBox);
        return new Scene(root);
    }
}
