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
import javafx.stage.Modality;

public class EditProfile {
    public static Scene createEditProfileScene(Stage stage) {
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

        Button returnToProfile = new Button("<");
        AnchorPane.setTopAnchor(returnToProfile, 0.0);
        AnchorPane.setLeftAnchor(returnToProfile, -15.0);

        Circle userPPCircle = new Circle(27, Color.DODGERBLUE);
        userPPCircle.setStroke(Color.BLACK);
        AnchorPane.setTopAnchor(userPPCircle, 20.0);
        AnchorPane.setLeftAnchor(userPPCircle, 15.0);

        Label username = new Label("ceren");
        AnchorPane.setTopAnchor(username, 30.0);
        AnchorPane.setLeftAnchor(username, 80.0);

        Label bio = new Label("your average romcom enjoyer");
        AnchorPane.setTopAnchor(bio, 50.0);
        AnchorPane.setLeftAnchor(bio, 80.0);

        Button editBio = new Button("Edit");
        AnchorPane.setTopAnchor(editBio, 25.0);
        AnchorPane.setRightAnchor(editBio, 20.0);
        editBio.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("EDIT ABOUT ME");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                TextField editField = new TextField(bio.getText());
                editField.setMaxWidth(250);
                Button saveBtn = new Button("Save");
                saveBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        String newBio = editField.getText();
                        bio.setText(newBio);
                        popupStage.close();
                        
                    }
                });

                newBox.getChildren().addAll(editField, saveBtn );
                Scene scene = new Scene(newBox, 300, 200);
                popupStage.setScene(scene);

                popupStage.show();
            }
        });
        

        Label PPChoosing = new Label("Choose a Profile Photo: ");
        AnchorPane.setTopAnchor(PPChoosing, 100.0);
        AnchorPane.setLeftAnchor(PPChoosing, 40.0);
        // the options will be added under the label later on

        Button save = new Button("Save the Changes");
        AnchorPane.setBottomAnchor(save, 10.0);
        AnchorPane.setRightAnchor(save, 150.0);

        Button deleteAccount = new Button("Delete Account");
        AnchorPane.setBottomAnchor(deleteAccount, 10.0);
        AnchorPane.setRightAnchor(deleteAccount, 10.0);

        deleteAccount.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Stage popupStage = new Stage();
                popupStage.setTitle("DELETING ACCOUNT");
                VBox newBox = new VBox(10);
                newBox.setPadding(new Insets(20));
                newBox.setAlignment(Pos.CENTER);
                Label label = new Label(
                        "You are deleting this account. If you delete this account, all of your data will be lost permanently. Are you sure you want to delete this account?");
                label.setWrapText(true);
                label.setMaxWidth(250);
                Button cancelBtn = new Button("Cancel");
                cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                    }
                });
                Button deleteBtn = new Button("Delete Account");
                deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        popupStage.close();
                        // the account should be deleted
                    }
                });

                newBox.getChildren().addAll(label, cancelBtn, deleteBtn);

                Scene scene = new Scene(newBox, 300, 200);
                popupStage.setScene(scene);

                popupStage.show();
            }
        });

        contentBox.getChildren().addAll(returnToProfile, userPPCircle, username, bio, PPChoosing, save, deleteAccount,editBio);
        root.getChildren().addAll(topMenu, contentBox);

        return new Scene(root);
    }
}
