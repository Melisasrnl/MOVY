package com.movies;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchFriends extends Application{
    public static void search(String username){}
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage arg0) throws Exception {
        TextField searchFriend= new TextField();
        String userSearched= searchFriend.getText();
        searchFriend.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button user1= new Button("mina");
        user1.setPrefSize(1200, 100);
        user1.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button user2= new Button("ekin");
        user2.setPrefSize(1200, 100);
        user2.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button user3= new Button("bensu");
        user3.setPrefSize(1200, 100);
        user3.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button user4= new Button("melisa");
        user4.setPrefSize(1200, 100);
        user4.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        VBox contains= new VBox(50, searchFriend, user1, user2, user3, user4);
        contains.setStyle("-fx-background-color: #0B0F1A;");
        contains.setMaxWidth(Double.MAX_VALUE);
        Scene testScene= new Scene(contains);
        arg0.setMaximized(true);
        arg0.setScene(testScene);
        arg0.show();
    }
}
