package com.movies;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchEngine extends Application{
     public static void filter(String genre, double rating){
     }
     public static void search(String movieName){
     }
     public static void main(String[] args) {
        launch(args);
     }
     @Override
     public void start(Stage arg0) throws Exception {
        TextField searchEngine= new TextField();
        String movieSearched= searchEngine.getText();
        searchEngine.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button movie1= new Button("interstellar");
        movie1.setPrefSize(200, 300);
        movie1.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button movie2= new Button("seven");
        movie2.setPrefSize(200, 300);
        movie2.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button movie3= new Button("notebook");
        movie3.setPrefSize(200, 300);
        movie3.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        Button movie4= new Button("titanic");
        movie4.setPrefSize(200, 300);
        movie4.setStyle("-fx-background-color: #282B35;"+"-fx-text-fill: #EAEAEA;");
        HBox movieContainer= new HBox(50,movie1, movie2, movie3, movie4);
        movieContainer.setAlignment(Pos.CENTER);
        VBox contains= new VBox(50, searchEngine, movieContainer);
        contains.setStyle("-fx-background-color: #0B0F1A;");
        contains.setMaxWidth(Double.MAX_VALUE);
        Scene testScene= new Scene(contains);
        arg0.setMaximized(true);
        arg0.setScene(testScene);
        arg0.show();
     }

}