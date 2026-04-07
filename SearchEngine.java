package com.movies;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchEngine {
   public static void filter(String genre, double rating) {
   }

   public static void search(String movieName) {
   }

   public static Scene choose(Stage primaryStage) {

      HBox topMenu = new TopMenu().createTopMenu(primaryStage);
      AnchorPane.setTopAnchor(topMenu, 10.0);
      AnchorPane.setLeftAnchor(topMenu, 5.0);
      AnchorPane.setRightAnchor(topMenu, 5.0);
      TextField searchEngine = new TextField();
      searchEngine.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
      ComboBox<String> genreFilter = new ComboBox<>();
      genreFilter.getItems().addAll("Action", "Comedy", "Drama");
      genreFilter.setPromptText("Choose a genre");
      genreFilter.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
      genreFilter.setPrefSize(150, 50);
      ComboBox<Integer> ratingFilter = new ComboBox<>();
      ratingFilter.getItems().addAll(1, 2, 3, 4, 5);
      ratingFilter.setPromptText("Choose a rating");
      ratingFilter.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
      ratingFilter.setPrefSize(150, 50);
      Button search= new Button("Search");
      search.setPrefSize(75, 50);
      search.setStyle("-fx-background-color: #282B35; -fx-text-fill: #EAEAEA;");
       search.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               //will be implemented
            }
        });
      HBox filters= new HBox(15, genreFilter, ratingFilter, search);
      filters.setAlignment(Pos.CENTER_RIGHT);
      filters.setStyle("-fx-background-color: #0B0F1A;");
      Button movie1 = new Button("interstellar");
      movie1.setPrefSize(200, 300);
      movie1.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
      Button movie2 = new Button("seven");
      movie2.setPrefSize(200, 300);
      movie2.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
      Button movie3 = new Button("notebook");
      movie3.setPrefSize(200, 300);
      movie3.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
      Button movie4 = new Button("titanic");
      movie4.setPrefSize(200, 300);
      movie4.setStyle("-fx-background-color: #282B35;" + "-fx-text-fill: #EAEAEA;");
      HBox movieContainer = new HBox(50, movie1, movie2, movie3, movie4);
      movieContainer.setAlignment(Pos.CENTER);
      VBox contains = new VBox(50, topMenu,searchEngine,filters, movieContainer);
      contains.setStyle("-fx-background-color: #0B0F1A;");
      contains.setMaxWidth(Double.MAX_VALUE);
      Scene testScene = new Scene(contains);
      return testScene;
   }

}