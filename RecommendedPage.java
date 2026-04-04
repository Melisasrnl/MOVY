package com.movies;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecommendedPage extends Application {
    public static void addToWatchlist(String recommended) {
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage arg0) throws Exception {
        Label filmPoster= new Label("a great movie");
        filmPoster.setPrefSize(400, 600);
        filmPoster.setStyle("-fx-background-color: #0B0F1A;" + "-fx-text-fill: #EAEAEA;");
        Label filmTitle= new Label("A GREAT MOVIE");
        filmTitle.setStyle("-fx-background-color: #0B0F1A;" + "-fx-text-fill: #EAEAEA;");
        Label aboutFilm= new Label("this is the greatest movie ever blah blah blah");
        aboutFilm.setStyle("-fx-background-color: #0B0F1A;" + "-fx-text-fill: #EAEAEA;");
        Button addWatchlist= new Button("Add to watchlist");
        addWatchlist.setStyle("-fx-background-color: #0B0F1A;" + "-fx-text-fill: #EAEAEA;");
        VBox right= new VBox(20, filmTitle, aboutFilm, addWatchlist);
        right.setPrefSize(700, 600);
        HBox movie= new HBox(40,filmPoster, right);
        VBox contains = new VBox(50, TopMenu.createTopMenu(),movie);
        contains.setStyle("-fx-background-color: #0B0F1A;");
        contains.setMaxWidth(Double.MAX_VALUE);
        Scene testScene = new Scene(contains);
        arg0.setMaximized(true);
        arg0.setScene(testScene);
        arg0.show();
    }
}
