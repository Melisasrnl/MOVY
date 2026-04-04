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

public class CreateNewGroupChat {
    public static Scene createNewGroupChatScene(Stage stage) {
        AnchorPane root = new AnchorPane();
        HBox topMenu = TopMenu.createTopMenu(stage);
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
        TextField chatName = new TextField("Write Group Name...");
        AnchorPane.setTopAnchor(chatName, 70.0);
        AnchorPane.setLeftAnchor(chatName, 20.0);
        AnchorPane.setRightAnchor(chatName, 20.0);
        // buttons are not working
        Button publicBtn = new Button("Public");
        AnchorPane.setTopAnchor(publicBtn, 120.0);
        AnchorPane.setLeftAnchor(publicBtn, 20.0);
        Button privateBtn = new Button("Private");
        AnchorPane.setTopAnchor(privateBtn, 120.0);
        AnchorPane.setLeftAnchor(privateBtn, 120.0);
        Label choosePPLabel = new Label("Choose a Profile Photo:");
        choosePPLabel.setStyle("-fx-font-size: 20px;");
        AnchorPane.setTopAnchor(choosePPLabel, 160.0);
        AnchorPane.setLeftAnchor(choosePPLabel, 20.0);
        // these will be modified after the picture URL become available
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
                stage.setScene(MemberSelectionGroupChat.createMemberSelectionGroupChatscene(stage));
            }
        });
        contentBox.getChildren().addAll(newGroupChatLbl, chatName, publicBtn, privateBtn, choosePPLabel, colorIconsForPP,
                otherPPOptions, searchMembers);
        root.getChildren().addAll(topMenu, contentBox);
        return new Scene(root);

    }
}
