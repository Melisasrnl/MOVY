package com.movies;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RecomByFriends {
    private String username;
    private ArrayList<Integer> movieIDs;

    public RecomByFriends(String username){
        this.username = username;
        this.movieIDs = DatabaseHandler.getMoviesFromRecomendedByFriends(username);
    }

    //ui for the favorites
    public Scene showRBF(Stage stage){
        HBox topMenu = new TopMenu().createTopMenu(stage);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #b2b1ae;");
        BorderPane mainLayout = new BorderPane();

        HBox topBar = new HBox();
        topBar.setSpacing(10);
        topBar.setPadding(new Insets(10));
        topBar.setStyle("-fx-background-color: #262523;");

        Button back = new Button("< Back");
        back.setOnAction(e -> {
            ProfilePage page = new ProfilePage();
            stage.setScene(page.createProfilePageScene(stage));
        });

        Label title = new Label("Recommended by Friends");
        title.setTextFill(Color.BEIGE);
        title.setFont(new Font(18));


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBar.getChildren().addAll(back, title);
        VBox headerBox = new VBox();
        headerBox.getChildren().addAll(topMenu, topBar);

        FlowPane moviePane = new FlowPane();
        moviePane.setPadding(new Insets(15));
        moviePane.setStyle("-fx-background-color: #b2b1ae;");
        moviePane.setHgap(20);
        moviePane.setVgap(20);

        for (Integer i: movieIDs) {
            VBox card = new VBox();
            card.setSpacing(5);
            card.setAlignment(Pos.CENTER);

            String posterPath = TmdbService.getMoviePhotoUrl(i);
            Image image = new Image(posterPath, 120, 160, true, true);
            ImageView posterView = new ImageView(image);

            Label name = new Label(TmdbService.getMovieName(i));

            Label who = new Label ("by " + DatabaseHandler.whoRecomended(username, i));

            card.getChildren().addAll(posterView, name, who);
            moviePane.getChildren().add(card);
        }

        ScrollPane scroll = new ScrollPane(moviePane);
        scroll.setStyle("-fx-background: #b2b1ae; -fx-background-color: #b2b1ae;");
        scroll.setFitToWidth(true);

        mainLayout.setTop(headerBox);
        mainLayout.setCenter(scroll);

        root.getChildren().add(mainLayout);

        Scene scene = new Scene(root, 800, 600);
        return scene;
    }    
}
