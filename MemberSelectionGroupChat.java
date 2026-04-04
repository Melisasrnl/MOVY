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

public class MemberSelectionGroupChat {
    public static Scene createMemberSelectionGroupChatscene(Stage stage) {
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

        TextField searchField = new TextField();
        searchField.setPromptText("Search for Members...");
        AnchorPane.setTopAnchor(searchField, 100.0);
        AnchorPane.setLeftAnchor(searchField, 10.0);
        AnchorPane.setRightAnchor(searchField, 10.0);

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
        createGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                // new scene will be added
            }
        });

        //followers and followings will be recommended automatically
        String ane[] = { "m", "a", "n", "i", "f", "e", "s", "t", "a", "tt" };
        for (String harf : ane) {
            HBox chatRow = new HBox(20);
            chatRow.setPadding(new Insets(5));
            chatRow.setStyle("-fx-border-color: lightgray; -fx-border-radius: 5; -fx-border-width: 1;");
            Circle chatPPCircle = new Circle(20, Color.LIGHTBLUE);
            Label harfs = new Label(harf); // replace with chatname
            harfs.setPrefWidth(150);

            VBox chatInfoBox = new VBox(3, harfs);
            chatInfoBox.setPrefWidth(200);
            Label isAlreadyAdded = new Label("+");
            VBox joinBox = new VBox(3, isAlreadyAdded);
            joinBox.setAlignment(Pos.CENTER_RIGHT);
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);
            chatRow.getChildren().addAll(chatPPCircle, chatInfoBox, spacer, joinBox);
            chatList.getChildren().add(chatRow);
        }  
        contentBox.getChildren().addAll(searchField, scrollPane, chatList,createGroup);
        root.getChildren().addAll(topMenu, contentBox);  

        return new Scene(root);
    }
}
